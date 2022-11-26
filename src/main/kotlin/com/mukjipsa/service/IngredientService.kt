package com.mukjipsa.service

import com.mukjipsa.domain.Ingredient
import com.mukjipsa.facade.dto.IngredientDto

interface IngredientService {

    fun getAllIngredient(): List<Ingredient>

    fun getIngredientByIdIn(ingredientIds: List<Int>): List<Ingredient>
}