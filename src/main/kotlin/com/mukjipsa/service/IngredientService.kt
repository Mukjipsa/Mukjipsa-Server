package com.mukjipsa.service

import com.mukjipsa.domain.Ingredient

interface IngredientService {
    fun getAllIngredient(): List<Ingredient>

    fun getIngredientByIdIn(ingredientIds: List<Int>): List<Ingredient>

}