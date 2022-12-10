package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.Ingredient
import com.mukjipsa.domain.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Int> {

    fun findByIdIn(ids: List<Int?>): List<Ingredient>

    fun findAllByNameContains(keyword: String): List<Ingredient>
}