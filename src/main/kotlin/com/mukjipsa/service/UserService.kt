package com.mukjipsa.service

import com.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.domain.User
import java.util.*

interface UserService {
    fun getAuthUserById(id: Int): CustomUserDetails
    fun getUserById(userId: Int): Optional<User>
}