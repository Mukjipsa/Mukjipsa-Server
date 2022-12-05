package com.mukjipsa.service

import com.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.domain.User

interface UserService {
    fun getAuthUserById(id: Int): CustomUserDetails
    fun getUserById(userId: Int): User
}