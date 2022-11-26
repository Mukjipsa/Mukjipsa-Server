package com.mukjipsa.facade

import com.mukjipsa.facade.dto.BookmarkResponseDto
import com.mukjipsa.service.BookmarkService
import com.mukjipsa.service.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BookmarkFacade(
        private val bookmarkService: BookmarkService,
        private val recipeService: RecipeService,
) {
    fun toggleBookmark(userId: Int, recipeId: Int) {
        val bookmark = bookmarkService.getBookmark(userId, recipeId)
        if (bookmark != null) {
            bookmarkService.deleteBookmark(bookmark.id)
        } else {
            bookmarkService.addBookmark(userId, recipeId)
        }
    }

    fun getBookmarkRecipe(userId: Int): BookmarkResponseDto {
        val recipeIds = bookmarkService.getBookmarkByUserId(userId).map { it.recipeId }
        val recipeList = recipeService.getRecipeByIdIn(recipeIds)
        return BookmarkResponseDto(
                data = recipeList,
                message = "북마크한 레시피 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }
}