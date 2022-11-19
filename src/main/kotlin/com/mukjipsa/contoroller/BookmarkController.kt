package com.mukjipsa.contoroller

import com.mukjipsa.facade.BookmarkFacade
import com.mukjipsa.facade.dto.BookmarkResponseDto
import com.mukjipsa.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookmark")
class BookmarkController(
    private val bookmarkFacade: BookmarkFacade,
    private val authService: AuthService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getBookmarkRecipe(): BookmarkResponseDto {
        val userId = authService.getUserId()
        return bookmarkFacade.getBookmarkRecipe(userId)
    }

    @PostMapping
    fun toggleBookmark(recipeId: Int) {
        val userId = authService.getUserId()
        bookmarkFacade.toggleBookmark(userId, recipeId)
    }
}