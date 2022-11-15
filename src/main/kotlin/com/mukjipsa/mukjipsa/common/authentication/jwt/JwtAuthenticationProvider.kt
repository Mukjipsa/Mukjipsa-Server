package com.mukjipsa.mukjipsa.common.authentication.jwt

import com.mukjipsa.mukjipsa.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    @Value("\${jwt.secret}")
    private val jwtAccessSecret: String?,
    private val userService: UserService,
) : AuthenticationProvider{
    override fun authenticate(authentication: Authentication?): Authentication? {
        val jwtToken = (authentication as JwtAuthenticationToken).jsonWebToken
        if(!jwtToken.isNullOrEmpty()) {
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
        TODO("Not yet implemented")
    }
}