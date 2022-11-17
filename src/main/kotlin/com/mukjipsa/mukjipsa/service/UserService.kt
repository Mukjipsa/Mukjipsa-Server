package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.mukjipsa.domain.User
import java.util.*

interface UserService {
    fun getAuthUserById(id: Int): CustomUserDetails
    fun getUserById(userId: Int): Optional<User>
}