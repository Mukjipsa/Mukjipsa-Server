package com.mukjipsa.service.impl

import com.mukjipsa.common.authentication.dto.CustomUserDetails
import com.mukjipsa.domain.User
import com.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.service.UserService
import org.springframework.stereotype.Service

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

    override fun getUserById(userId: Int): User {
        return userRepository.findById(userId).get()
    }

    override fun updateIngredientRound(user: User) {
        user.ingredientsRound += 1
        userRepository.save(user)
    }
}