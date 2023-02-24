package com.mukjipsa.facade

import com.mukjipsa.facade.dto.BookmarkResponseDto
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.RecipeDto
import com.mukjipsa.service.BookmarkService
import com.mukjipsa.service.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BookmarkFacade(
        private val bookmarkService: BookmarkService,
        private val recipeService: RecipeService,
) {
    fun toggleBookmark(userId: Int, recipeId: Int): Boolean {
        val bookmark = bookmarkService.getBookmark(userId, recipeId)
        return if (bookmark != null) {
            bookmarkService.deleteBookmark(bookmark.id)
            false
        } else {
            bookmarkService.addBookmark(userId, recipeId)
            true
        }
    }

    fun getBookmarkRecipe(userId: Int): BookmarkResponseDto {
        val recipeIds = bookmarkService.getBookmarkByUserId(userId).map { it.recipeId }
        val recipeList = recipeService.getRecipeByIdIn(recipeIds).map{
            RecipeDto(
                content = it.content,
                createdAt = it.createdAt,
                id = it.id,
                ingredients = it.ingredients.map {
                    IngredientDto(
                        categoryType = it.category.name,
                        id = it.id,
                        isHave = null,
                        name = it.name,
                        categoryId = it.category.id
                    )
                },
                link = it.link,
                thumbnail = it.thumbnail,
                title = it.title,
                updatedAt = it.updatedAt,
                isBookmarked = true
            )
        }
        return BookmarkResponseDto(
                data = recipeList,
                message = "북마크한 레시피 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }
}