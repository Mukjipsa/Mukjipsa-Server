package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.RecipeIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientRepository : JpaRepository<RecipeIngredient, Int> {
}