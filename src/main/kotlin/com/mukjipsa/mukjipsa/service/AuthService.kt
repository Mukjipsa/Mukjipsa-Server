package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.service.dto.LoginResponse
import javax.transaction.Transactional

interface AuthService {
    fun getUserId(): Int
    @Transactional
    fun loginWithToken(providerName: String, userToken: String): LoginResponse?
}