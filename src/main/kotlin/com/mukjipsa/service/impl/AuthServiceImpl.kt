package com.mukjipsa.service.impl


import com.fasterxml.jackson.databind.ObjectMapper

import com.mukjipsa.common.authentication.dto.CustomUserDetails
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
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*
import java.util.concurrent.TimeUnit
import javax.transaction.Transactional


@Service
class AuthServiceImpl(
        private val jwtAuthenticationProvider: JwtAuthenticationProvider,
        private val userRepository: UserRepository,
        private val appleClient: AppleClient,
        private val kakoClient: KakaoClient,
        private val redisTemplate: RedisTemplate<String, String>,
        @Value("\${jwt.refresh-expires-in}")
        private val refresh_token_expire_time: Long = 2592000,
) : AuthService {


    override fun getUserId(): Int {
        return (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).getUserDetails()?.id
                ?: throw UserNotFoundException("user가 존재하지 않습니다.")
    }

    override fun refreshWithToken(userToken: String): LoginResponse {
        val user: CustomUserDetails = jwtAuthenticationProvider.verifyAndDecodeRefreshToken(userToken);

        val refreshToken = redisTemplate.opsForValue()[user.email]
        if (refreshToken != userToken) {
            throw BusinessException(ErrorCode.REFRESH_TOKEN_DOESNT_MATCH);
        }

        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(user.id)
        val newRefreshToken: String = jwtAuthenticationProvider.generateRefreshToken(user.id)

        redisTemplate.opsForValue().set(
                user.email,
                newRefreshToken,
                refresh_token_expire_time,
                TimeUnit.MILLISECONDS
        );

        return LoginResponse(accessToken, newRefreshToken)
    }

    @Transactional
    override fun loginWithToken(providerName: String, userToken: String): LoginResponse {
        val user: User = getUserProfileByToken(providerName, userToken);

        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(user.id)
        val refreshToken: String = jwtAuthenticationProvider.generateRefreshToken(user.id)

        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        redisTemplate.opsForValue().set(
                user.email,
                refreshToken,
                refresh_token_expire_time,
                TimeUnit.MILLISECONDS
        );

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
