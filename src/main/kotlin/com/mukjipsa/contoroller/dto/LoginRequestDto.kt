package com.mukjipsa.contoroller.dto

data class LoginRequestDto(
    val provider: String,
    val ssoToken: String,
)
