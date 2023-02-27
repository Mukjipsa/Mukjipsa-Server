package com.mukjipsa.service

import com.mukjipsa.domain.Recipe
import com.mukjipsa.facade.dto.RecipeDto
import java.util.*

interface RecipeService {
    fun getRecipeByIdIn(recipeIds: List<Int>): List<Recipe>
    fun getAllRecipe(): List<Recipe>
    fun getRecipe(recipeId: Int): Recipe
    fun saveSearchKeyword(userId: Int, keyword: String)

    fun getRecipeByKeyword(keyword: String): List<Recipe>

}