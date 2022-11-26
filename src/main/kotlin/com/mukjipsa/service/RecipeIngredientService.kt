package com.mukjipsa.service

import com.mukjipsa.domain.RecipeIngredient

interface RecipeIngredientService {
    fun getIngredientByRecipeId(recipeId: Int): List<RecipeIngredient>
}