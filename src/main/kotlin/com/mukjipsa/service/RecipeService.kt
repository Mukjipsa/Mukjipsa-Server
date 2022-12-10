package com.mukjipsa.service

import com.mukjipsa.domain.Recipe
import com.mukjipsa.facade.dto.RecipeDto
import java.util.*

interface RecipeService {
    fun getRecipeByIdIn(recipeIds: List<Int>): List<RecipeDto>
    fun getAllRecipe(): List<Recipe>
    fun getRecipe(recipeId: Int): Optional<Recipe>
    fun getRecipeByKeyword(keyword: String): List<Recipe>

}