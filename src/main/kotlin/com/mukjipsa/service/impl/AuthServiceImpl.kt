package com.mukjipsa.service.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.mukjipsa.common.authentication.jwt.JwtAuthenticationProvider
import com.mukjipsa.common.exception.UserNotFoundException
import com.mukjipsa.domain.User
import com.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.KakaoProfile
import com.mukjipsa.service.dto.LoginResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import javax.transaction.Transactional


@Service
class AuthServiceImpl(
        private val jwtAuthenticationProvider: JwtAuthenticationProvider,
        private val userRepository: UserRepository,
) : AuthService {
    override fun getUserId(): Int {
//        return (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).getUserDetails()?.id
//            ?: throw UserNotFoundException("user가 존재하지 않습니다.")
        // login 구현 전까지 개발용으로 사용.
        return 1
    }

    @Transactional
    override fun loginWithToken(providerName: String, userToken: String): LoginResponse {
        val user: User = getUserProfileByToken(providerName, userToken)
        val accessToken: String = jwtAuthenticationProvider.generateAccessToken(user.id)
        val refreshToken: String = jwtAuthenticationProvider.generateRefreshToken(user.id)
        return LoginResponse(accessToken, refreshToken)
    }

    private fun getUserAttributesByToken(userToken: String): KakaoProfile? {
        val wc = WebClient.create("https://kapi.kakao.com/v2/user/me")
        val response: String? = wc.post()
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
}