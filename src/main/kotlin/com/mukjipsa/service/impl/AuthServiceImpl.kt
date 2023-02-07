package com.mukjipsa.service.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mukjipsa.common.authentication.jwt.JwtAuthenticationProvider
import com.mukjipsa.common.exception.BusinessException
import com.mukjipsa.common.exception.UserNotFoundException
import com.mukjipsa.common.exception.response.ErrorCode
import com.mukjipsa.contoroller.AppleClient
import com.mukjipsa.domain.User
import com.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.KakaoProfile
import com.mukjipsa.service.dto.LoginResponse
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.transaction.Transactional

@Service
class AuthServiceImpl(
        private val jwtAuthenticationProvider: JwtAuthenticationProvider,
        private val userRepository: UserRepository,
        private val appleClient: AppleClient,
) : AuthService {
    override fun getUserId(): Int {
//        return (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).getUserDetails()?.id
//            ?: throw UserNotFoundException("user가 존재하지 않습니다.")
        // login 구현 전까지 개발용으로 사용.
        return 1
    }


    @Transactional
    override fun loginWithToken(providerName: String, userToken: String): LoginResponse {

        var user: User;
        when(providerName) {
            "kakao" -> {
                user = getUserProfileByToken(providerName, userToken);
            }
            "apple" -> {
                val response = appleClient.getAppleAuthPublicKey();
                val headerOfIdentityToken = userToken.substring(0, userToken.indexOf("."))
                val header = ObjectMapper().readValue(String(Base64.getDecoder().decode(headerOfIdentityToken), "UTF-8" as Charset), Map::class.java)
                val key = response.getMatchedKeyBy(header["kid"] as String, header["alg"] as String)
                        ?: throw BusinessException(ErrorCode.FAILED_TO_FIND_AVALIABLE_RSA)

                val nBytes = Base64.getUrlDecoder().decode(key.n)
                val eBytes = Base64.getUrlDecoder().decode(key.e)

                val n = BigInteger(1, nBytes)
                val e = BigInteger(1, eBytes)

                val publicKeySpec = RSAPublicKeySpec(n, e)
                val keyFactory = KeyFactory.getInstance(key.kty)
                val publicKey = keyFactory.generatePublic(publicKeySpec)

                val userInfo = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(userToken).body
                val userInfoObject = JsonParser.parseString(Gson().toJson(userInfo)) as JsonObject
                val ssoId = userInfoObject["sub"] as String
                val email = userInfoObject["email"] as String
                user = User(
                        email = email,
                        provider = providerName,
                        ssoId = ssoId,
                )
                userRepository.save(user)

//
//                val result= StringBuffer();
//                try {
//                    val url = URL("https://appleid.apple.com/auth/keys");
//                    val conn = url.openConnection() as HttpURLConnection
//                    conn.requestMethod = "GET"
//                    val br = BufferedReader(InputStreamReader(conn.inputStream));
//                    var line = "";
//                    while ((br.readLine().also{ line = it }) != null) {
//                        result.append(line);
//                    }
//                } catch (e: IOException) {
//                    throw BusinessException(ErrorCode.FAILED_TO_VALIDATE_APPLE_LOGIN);
//                }
//
//                val keys =  JsonParser.parseString(result.toString()) as JsonObject;
//                val keyArray = keys.get("keys") as JsonArray;
//
//                //클라이언트로부터 가져온 identity token String decode
//                val decodeArray = userToken.split("\\.").toTypedArray();
//                val header = String(Base64.getDecoder().decode(decodeArray[0]));
//
//                //apple에서 제공해주는 kid값과 일치하는지 알기 위해
//                val kid = (JsonParser.parseString(header) as JsonObject)["kid"] as JsonElement
//                val alg = (JsonParser.parseString(header) as JsonObject)["alg"] as JsonElement
//
//                //써야하는 Element (kid, alg 일치하는 element)
//                var availableObject: JsonObject? = null;
//                for (i in 0 until keyArray.size()) {
//                    val appleObject = keyArray[i] as JsonObject
//                    val appleKid = appleObject["kid"]
//                    val appleAlg = appleObject["alg"]
//                    if (appleKid == kid && appleAlg == alg) {
//                        availableObject = appleObject
//                        break
//                    }
//                }
//                if (availableObject == null) throw BusinessException(ErrorCode.FAILED_TO_FIND_AVALIABLE_RSA)
//
//                val publicKey = getPublicKey(availableObject)

            }
            else -> throw BusinessException(ErrorCode.INVALID_PROVIDER_NAME)
        }


        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(user.id)
        val refreshToken: String = jwtAuthenticationProvider.generateRefreshToken(user.id)
        return LoginResponse(accessToken, refreshToken)
    }

    private fun getUserAttributesByToken(userToken: String): KakaoProfile? {
        val wc = WebClient.create("https://kapi.kakao.com/v2/user/me")
        val response: String? = wc.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer $userToken")
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        val objectMapper = ObjectMapper()
        var kakaoProfile: KakaoProfile? = null

        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfile::class.java)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
        return kakaoProfile
    }

    private fun getUserProfileByToken(providerName: String, userToken: String): User {
        val userAttributesByToken =
                getUserAttributesByToken(userToken)
        val kakaoId = userAttributesByToken?.id ?: throw UserNotFoundException("error sso")
        val user = userRepository.findBySsoId(kakaoId)
        return if (user == null) {
            val newUser = User(
                    email = "sso",
                    provider = providerName,
                    ssoId = kakaoId,
            )
            userRepository.save(newUser)
        } else {
            user
        }
    }


//    private fun getPublicKey(availableObject: JsonObject): PublicKey {
//        val nStr = availableObject["n"].toString()
//        val eStr = availableObject["e"].toString()
//
//        val nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length - 1))
//        val eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length - 1))
//
//        val n = BigInteger(1, nBytes)
//        val e = BigInteger(1, eBytes)
//
//        try {
//            val publicKeySpec = RSAPublicKeySpec(n, e)
//            val keyFactory = KeyFactory.getInstance("RSA")
//            val publicKey = keyFactory.generatePublic(publicKeySpec)
//            return publicKey
//        } catch (exception: Exception) {
//            throw BusinessException(ErrorCode.FAILED_TO_FIND_AVALIABLE_RSA)
//        }
//    }
}