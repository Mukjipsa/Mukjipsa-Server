package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.RecipeRepository
import com.mukjipsa.mukjipsa.service.RecipeService
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository
) : RecipeService {
}