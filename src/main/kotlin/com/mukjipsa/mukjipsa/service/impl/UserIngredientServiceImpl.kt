package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.mukjipsa.service.UserIngredientService
import org.springframework.stereotype.Service

@Service
class UserIngredientServiceImpl(
    private val userIngredientRepository: RecipeIngredientRepository
) : UserIngredientService {
}