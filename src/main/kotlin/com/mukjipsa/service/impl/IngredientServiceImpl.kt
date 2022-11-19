package com.mukjipsa.service.impl

import com.mukjipsa.domain.Ingredient
import com.mukjipsa.infrastructure.IngredientRepository
import com.mukjipsa.service.IngredientService
import org.springframework.stereotype.Service

@Service
class IngredientServiceImpl(
    private val ingredientRepository: IngredientRepository
) : IngredientService {
    override fun getAllIngredient(): List<Ingredient> {
        return ingredientRepository.findAll()
    }
}