package com.mukjipsa.service

import com.mukjipsa.service.dto.LoginResponse
import javax.transaction.Transactional

interface AuthService {
    fun getUserId(): Int

    @Transactional
    fun loginWithToken(providerName: String, userToken: String): Pair<String, String>

    @Transactional
    fun refreshWithToken(userToken: String): Pair<String, String>
}