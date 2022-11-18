package com.mukjipsa.mukjipsa.common.authentication.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    @Autowired
    private val authenticationManager: AuthenticationManager
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val accessToken = request.getHeader("Authorization")?.substring(7) // remove 'Bearer '
        if (!accessToken.isNullOrBlank()) {
            // 인증되지않은 Authentication 객체 생성
            val jwtAuthenticationToken = JwtAuthenticationToken(accessToken)
            // provider를 통해 authentication 진행
            val authentication = this.authenticationManager.authenticate(jwtAuthenticationToken)
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }
}