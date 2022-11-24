package com.mukjipsa.service.impl

import com.mukjipsa.domain.UserIngredient
import com.mukjipsa.infrastructure.UserIngredientRepository
import com.mukjipsa.service.UserIngredientService
import org.springframework.stereotype.Service

@Service
class UserIngredientServiceImpl(
    private val userIngredientRepository: UserIngredientRepository
) : UserIngredientService {

    override fun getIngredientByUserId(userId: Int): List<UserIngredient>{
        return userIngredientRepository.findAllByUserId(userId)
    }
}