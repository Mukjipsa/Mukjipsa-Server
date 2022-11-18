package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.EssentialIngredientRepository
import com.mukjipsa.mukjipsa.service.EssentialIngredientService
import org.springframework.stereotype.Service

@Service
class EssentialIngredientServiceImpl(
    private val essentialIngredientRepository: EssentialIngredientRepository
) : EssentialIngredientService{

}