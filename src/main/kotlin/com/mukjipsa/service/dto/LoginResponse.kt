package com.mukjipsa.service.dto

data class LoginResponse(
        val accessToken: String,
        val refreshToken: String,
)
