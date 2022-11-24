package com.mukjipsa.service.impl

import com.mukjipsa.domain.RecipeIngredient
import com.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.service.RecipeIngredientService
import org.springframework.stereotype.Service

@Service
class RecipeIngredientServiceImpl(
        private val recipeIngredientRepository: RecipeIngredientRepository
) : RecipeIngredientService {

    override fun getIngredientByRecipeId(recipeId: Int): List<RecipeIngredient> {
        return recipeIngredientRepository.findByRecipeId(recipeId)
    }
}