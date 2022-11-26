package com.mukjipsa.service.impl

import com.mukjipsa.domain.Ingredient
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.infrastructure.IngredientRepository
import com.mukjipsa.infrastructure.UserIngredientRepository
import com.mukjipsa.service.IngredientService
import org.springframework.stereotype.Service

@Service
class IngredientServiceImpl(
        private val ingredientRepository: IngredientRepository
) : IngredientService {

    override fun getAllIngredient(): List<Ingredient> {
        return ingredientRepository.findAll()
    }

    override fun getIngredientByIdIn(ingredientIds: List<Int?>): List<IngredientDto> {
        return ingredientRepository.findByIdIn(ingredientIds).map{
            IngredientDto(
                categoryType = it.category.name,
                id = it.id,
                name = it.name,
                isHave = true,
            )
        }
    }
}