package com.mukjipsa.service.impl

import com.mukjipsa.domain.Bookmark
import com.mukjipsa.infrastructure.BookmarkRepository
import com.mukjipsa.service.BookmarkService
import org.springframework.stereotype.Service

@Service
class BookmarkServiceImpl(
        private val bookmarkRepository: BookmarkRepository,
) : BookmarkService {
    override fun getBookmark(userId: Int, recipeId: Int): Bookmark? {
        return bookmarkRepository.findByUserIdAndRecipeId(userId, recipeId)
    }

    override fun deleteBookmark(bookmarkId: Int) {
        bookmarkRepository.deleteById(bookmarkId)
    }

    override fun addBookmark(userId: Int, recipeId: Int) {
        bookmarkRepository.save(Bookmark(userId = userId, recipeId = recipeId))
    }

    override fun getBookmarkByUserId(userId: Int): List<Bookmark> {
        return bookmarkRepository.findAllByUserId(userId)
    }
}