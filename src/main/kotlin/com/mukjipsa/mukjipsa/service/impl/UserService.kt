package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.UserRepository
import com.mukjipsa.mukjipsa.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
}