package com.mukjipsa.mukjipsa.service

import com.mukjipsa.mukjipsa.domain.Bookmark

interface BookmarkService {
    fun getBookmark(userId: Int, recipeId: Int): Bookmark?
    fun deleteBookmark(bookmarkId: Int)
    fun addBookmark(userId: Int, recipeId: Int)
    fun getBookmarkByUserId(userId: Int): List<Bookmark>
}