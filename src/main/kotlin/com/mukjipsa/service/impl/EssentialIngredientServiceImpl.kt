package com.mukjipsa.service.impl

import com.mukjipsa.infrastructure.EssentialIngredientRepository
import com.mukjipsa.service.EssentialIngredientService
import org.springframework.stereotype.Service

@Service
class EssentialIngredientServiceImpl(
        private val essentialIngredientRepository: EssentialIngredientRepository
) : EssentialIngredientService {
    override fun getAllIds(): List<Int> {
        return essentialIngredientRepository.findAll().map { it.ingredientId }
    }

}