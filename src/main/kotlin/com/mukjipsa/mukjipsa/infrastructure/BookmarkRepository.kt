package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.Bookmark
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository : JpaRepository<Bookmark, Int> {

    fun findByUserIdAndRecipeId(userId: Int, recipeId: Int): Bookmark?

    fun findAllByUserId(userId: Int): List<Bookmark>
}