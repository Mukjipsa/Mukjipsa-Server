package com.mukjipsa.service

import com.mukjipsa.domain.Ingredient

interface IngredientService {
    fun getAllIngredient(): List<Ingredient>
}