package com.mukjipsa.mukjipsa.facade

import com.mukjipsa.mukjipsa.service.RecipeIngredientService
import com.mukjipsa.mukjipsa.service.RecipeService
import com.mukjipsa.mukjipsa.service.UserIngredientService
import org.springframework.stereotype.Service

@Service
class RecipeFacade(
    private val recipeService: RecipeService,
    private val userIngredientService: UserIngredientService,
    private val recipeIngredientService: RecipeIngredientService,
) {
}