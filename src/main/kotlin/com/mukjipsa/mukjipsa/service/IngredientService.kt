package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.domain.Ingredient

interface IngredientService {
    fun getAllIngredient(): List<Ingredient>
}