package com.mukjipsa.service.impl

import com.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.service.RecipeIngredientService
import org.springframework.stereotype.Service

@Service
class RecipeIngredientServiceImpl(
    private val recipeIngredientRepository: RecipeIngredientRepository
) : RecipeIngredientService {
}