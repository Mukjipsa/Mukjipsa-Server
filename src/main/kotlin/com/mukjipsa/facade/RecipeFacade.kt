package com.mukjipsa.facade

import com.mukjipsa.service.RecipeIngredientService
import com.mukjipsa.service.RecipeService
import com.mukjipsa.service.UserIngredientService
import org.springframework.stereotype.Service

@Service
class RecipeFacade(
    private val recipeService: RecipeService,
    private val userIngredientService: UserIngredientService,
    private val recipeIngredientService: RecipeIngredientService,
) {
}