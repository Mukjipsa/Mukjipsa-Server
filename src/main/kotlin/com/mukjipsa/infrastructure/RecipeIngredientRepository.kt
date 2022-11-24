package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.RecipeIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientRepository : JpaRepository<RecipeIngredient, Int> {
    fun findByRecipeId(recipeId: Int): List<RecipeIngredient>
}