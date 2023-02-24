package com.mukjipsa.contoroller

import com.mukjipsa.contoroller.dto.LoginRequestDto
import com.mukjipsa.contoroller.dto.RefreshRequestDto
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.dto.LoginResponse
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
        val tokenDto = authService.loginWithToken(loginRequestDto.provider, loginRequestDto.ssoToken)
        return ResponseEntity.ok().body(tokenDto)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshRequestDto: RefreshRequestDto): ResponseEntity<LoginResponse> {
        val tokenDto = authService.refreshWithToken(refreshRequestDto.refreshToken)
        return ResponseEntity.ok().body(tokenDto)
    }
}