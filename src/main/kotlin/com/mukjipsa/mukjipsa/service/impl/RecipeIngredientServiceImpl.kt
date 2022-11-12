package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.mukjipsa.service.RecipeIngredientService
import org.springframework.stereotype.Service

@Service
class RecipeIngredientServiceImpl(
    private val recipeIngredientRepository: RecipeIngredientRepository
) : RecipeIngredientService {
}