package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.domain.Ingredient
import com.mukjipsa.mukjipsa.infrastructure.IngredientRepository
import com.mukjipsa.mukjipsa.service.IngredientService
import org.springframework.stereotype.Service

@Service
class IngredientServiceImpl(
    private val ingredientRepository: IngredientRepository
) : IngredientService {
    override fun getAllIngredient(): List<Ingredient> {
        return ingredientRepository.findAll()
    }
}