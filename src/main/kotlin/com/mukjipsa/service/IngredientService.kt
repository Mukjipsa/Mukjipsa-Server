package com.mukjipsa.service

import com.mukjipsa.domain.Ingredient
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.RecipeDto

interface IngredientService {
    fun getAllIngredient(): List<Ingredient>

    fun getIngredientByIdIn(ingredientIds: List<Int>): List<Ingredient>

}