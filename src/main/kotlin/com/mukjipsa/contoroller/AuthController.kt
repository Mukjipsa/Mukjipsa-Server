package com.mukjipsa.contoroller

import com.mukjipsa.contoroller.dto.LoginRequestDto
import com.mukjipsa.contoroller.dto.RefreshRequestDto
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.LoginResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService,
) {
    @PostMapping
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponse> {
        val (accessToken, refreshToken) = authService.loginWithToken(loginRequestDto.provider, loginRequestDto.ssoToken)
        return ResponseEntity.ok().body(LoginResponse(HttpStatus.OK.value(),true,"로그인 성공", accessToken, refreshToken))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshRequestDto: RefreshRequestDto): ResponseEntity<LoginResponse> {
        val (accessToken, refreshToken) = authService.refreshWithToken(refreshRequestDto.refreshToken)
        return ResponseEntity.ok().body(LoginResponse(HttpStatus.OK.value(),true,"토큰 재발급 성공", accessToken, refreshToken))
    }
}