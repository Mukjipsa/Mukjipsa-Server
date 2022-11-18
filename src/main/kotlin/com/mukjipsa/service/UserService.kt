package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.mukjipsa.domain.User

interface UserService {
    fun getAuthUserById(id: Int): CustomUserDetails
}