package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Int> {
}