package com.mukjipsa.service.impl


import com.fasterxml.jackson.databind.ObjectMapper
import com.mukjipsa.common.authentication.jwt.JwtAuthenticationProvider
import com.mukjipsa.common.authentication.jwt.JwtAuthenticationToken
import com.mukjipsa.common.exception.BusinessException
import com.mukjipsa.common.exception.UserNotFoundException
import com.mukjipsa.common.exception.response.ErrorCode
import com.mukjipsa.contoroller.AppleClient
import com.mukjipsa.contoroller.KakaoClient
import com.mukjipsa.domain.User
import com.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.KakaoProfile
import com.mukjipsa.service.dto.LoginResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.transaction.Transactional


@Service
class AuthServiceImpl(
        private val jwtAuthenticationProvider: JwtAuthenticationProvider,
        private val userRepository: UserRepository,
        private val appleClient: AppleClient,
        private val kakoClient: KakaoClient,
) : AuthService {
    override fun getUserId(): Int {
        return (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).getUserDetails()?.id
                ?: throw UserNotFoundException("user가 존재하지 않습니다.")
    }

    override fun refreshWithToken(userToken: String): LoginResponse {
        val userId: Int = jwtAuthenticationProvider.verifyAndDecodeRefreshToken(userToken);

        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(userId)
        val refreshToken: String = jwtAuthenticationProvider.generateRefreshToken(userId)

        return LoginResponse(accessToken, refreshToken)
    }

    @Transactional
    override fun loginWithToken(providerName: String, userToken: String): LoginResponse {
        val user: User = getUserProfileByToken(providerName, userToken);

        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(user.id)
        val refreshToken: String = jwtAuthenticationProvider.generateRefreshToken(user.id)

        return LoginResponse(accessToken, refreshToken)
    }

    private fun getKakaoUserInfoByToken(userToken: String): KakaoProfile {
        val kakaoProfile: KakaoProfile = kakoClient.getUserInfo("Bearer $userToken")
        return kakaoProfile

    }

    private fun getAppleUserInfoByToken(userToken: String): Claims {
        val response = appleClient.getAppleAuthPublicKey();
        val headerOfIdentityToken = userToken.substring(0, userToken.indexOf("."))
        val header = ObjectMapper().readValue(String(Base64.getDecoder().decode(headerOfIdentityToken)), Map::class.java)

        val key = response.getMatchedKeyBy(header["kid"] as String, header["alg"] as String)
                ?: throw BusinessException(ErrorCode.FAILED_TO_FIND_AVALIABLE_RSA)

        val nBytes = Base64.getUrlDecoder().decode(key.n)
        val eBytes = Base64.getUrlDecoder().decode(key.e)

        val n = BigInteger(1, nBytes)
        val e = BigInteger(1, eBytes)

        val publicKeySpec = RSAPublicKeySpec(n, e)
        val keyFactory = KeyFactory.getInstance(key.kty)
        val publicKey = keyFactory.generatePublic(publicKeySpec)

        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(userToken).body
    }

    private fun getUserProfileByToken(providerName: String, userToken: String): User {
        var ssoId = ""
        var email = ""
        when (providerName) {
            "kakao" -> {
                val userInfo = getKakaoUserInfoByToken(userToken)
                if (userInfo.id != null && userInfo.kakao_account?.email != null) {
                    ssoId = userInfo.id as String
                    email = userInfo.kakao_account?.email as String
                }
            }

            "apple" -> {
                val userInfo = getAppleUserInfoByToken(userToken)

                ssoId = userInfo["sub"] as String
                email = userInfo["email"] as String
            }

            else -> throw BusinessException(ErrorCode.INVALID_PROVIDER_NAME)
        }

        val user = userRepository.findBySsoId(ssoId)
        return if (user == null) {
            val newUser = User(
                    email = email,
                    provider = providerName,
                    ssoId = ssoId,
                    ingredientsRound = 0,
            )
            userRepository.save(newUser)
        } else {
            user
        }
    }
}
