package com.mukjipsa.service.impl

import com.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.service.UserIngredientService
import org.springframework.stereotype.Service

@Service
class UserIngredientServiceImpl(
    private val userIngredientRepository: RecipeIngredientRepository
) : UserIngredientService {
}