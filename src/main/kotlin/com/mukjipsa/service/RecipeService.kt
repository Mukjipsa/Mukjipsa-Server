package com.mukjipsa.service

import com.mukjipsa.facade.dto.RecipeDto

interface RecipeService {
    fun getRecipeByIdIn(recipeIds: List<Int>): List<RecipeDto>
}