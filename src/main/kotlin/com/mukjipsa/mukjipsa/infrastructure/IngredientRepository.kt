package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Int> {
}