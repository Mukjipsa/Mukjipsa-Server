package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.facade.dto.RecipeDto

interface RecipeService {
    fun getRecipeByIdIn(recipeIds: List<Int>): List<RecipeDto>
}