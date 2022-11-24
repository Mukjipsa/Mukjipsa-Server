package com.mukjipsa.contoroller

import com.mukjipsa.contoroller.dto.LoginRequestDto
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.LoginResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/login")
class AuthController(
        private val authService: AuthService,
) {
    @PostMapping
    fun login(@RequestBody loginRequestDto: LoginRequestDto): LoginResponse {
        return authService.loginWithToken(loginRequestDto.provider, loginRequestDto.ssoToken)
    }
}