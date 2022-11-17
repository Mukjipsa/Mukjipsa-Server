package com.mukjipsa.mukjipsa.facade

import com.mukjipsa.mukjipsa.service.BookmarkService
import org.springframework.stereotype.Service

@Service
class BookmarkFacade(
    private val bookmarkService: BookmarkService,
) {
    fun toggleBookmark(userId: Int, recipeId: Int) {
        val bookmark = bookmarkService.getBookmark(userId, recipeId)
        if (bookmark != null) {
            bookmarkService.deleteBookmark(bookmark.id)
        } else {
            bookmarkService.addBookmark(userId, recipeId)
        }
    }
}