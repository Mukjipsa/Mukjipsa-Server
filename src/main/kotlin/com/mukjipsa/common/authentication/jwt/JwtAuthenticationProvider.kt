package com.mukjipsa.common.authentication.jwt

import com.mukjipsa.common.exception.UserNotFoundException
import com.mukjipsa.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtAuthenticationProvider(
        @Value("\${jwt.secret}")
        private val jwtAccessSecret: String?,
        private val userService: UserService,
        // alpha:(3600 * 24) 그 외: 3600
        @Value("\${jwt.access-expires-in}")
        private val accessExpiresIn: Long = 3600,
        // 30일 3600 * 24 * 30 = 2592000
        @Value("\${jwt.refresh-expires-in}")
        private val refreshExpiresIn: Long = 2592000,
        @Value("\${jwt.refresh-secret}")
        private val refreshSecretKey: String? = null
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        val jwtToken = (authentication as JwtAuthenticationToken).jsonWebToken
        if (!jwtToken.isNullOrEmpty()) {
            val claims = JwtUtil(jwtAccessSecret).decodeToken(jwtToken)
            val userInfo = userService.getAuthUserById(claims?.get("id").toString().toInt())
            return JwtAuthenticationToken(
                    principal = userInfo.email,
                    credentials = userInfo.password,
                    userDetails = userInfo,
            )
        }
        return null
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    fun verifyAndDecodeRefreshToken(refreshToken: String): Int {
        val decode = JwtUtil(refreshSecretKey).decodeToken(refreshToken)
        val userId = decode?.get("id") as Int? ?: throw UserNotFoundException("user not found")
        val userInfo = userService.getAuthUserById(userId)
                ?: throw UserNotFoundException("user not found")
        SecurityContextHolder.getContext().authentication =
                JwtAuthenticationToken(
                        principal = userInfo.email,
                        credentials = userInfo.password,
                        userDetails = userInfo
                )
        return userId
    }

    fun getClaimAndKey(
            userPk: Int,
            tokenSecretKey: String?
    ): Pair<Claims, Key?> {
        val claims = Jwts.claims()
        claims["id"] = userPk
        val key = Keys.hmacShaKeyFor(tokenSecretKey?.toByteArray()?.copyOf(64))
        return Pair(claims, key)
    }

    fun generateAccessToken(userPk: Int): String {
        val (claims, key) = getClaimAndKey(userPk, jwtAccessSecret)
        return JwtUtil(this.jwtAccessSecret).encodeAccessToken(claims, key, accessExpiresIn)
    }

    fun generateRefreshToken(userPk: Int): String {
        val (claims, key) = getClaimAndKey(userPk, refreshSecretKey)
        return JwtUtil(this.refreshSecretKey).encodeRefreshToken(claims, key, refreshExpiresIn)
    }

}