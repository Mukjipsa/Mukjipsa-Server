package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Int> {

    fun findByIdIn(ids: List<Int>): List<Recipe>

    fun findAllByContentLike(keywords: String): List<Recipe>
}