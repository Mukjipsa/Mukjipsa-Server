package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.mukjipsa.domain.User
import com.mukjipsa.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.mukjipsa.service.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun getAuthUserById(id: Int): CustomUserDetails {
        val user = userRepository.findById(id).get()
        return CustomUserDetails(
            id = user.id,
            email = user.email,
            provider = user.provider,
            ssoId = user.ssoId,
        )
    }
}