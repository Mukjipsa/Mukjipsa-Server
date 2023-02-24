package com.mukjipsa.contoroller

import com.mukjipsa.facade.BookmarkFacade
import com.mukjipsa.facade.dto.BookmarkResponseDto
import com.mukjipsa.facade.dto.BookmarkToggleResponseDto
import com.mukjipsa.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/{recipeId}")
    fun toggleBookmark(@PathVariable recipeId: Int): ResponseEntity<BookmarkToggleResponseDto> {
        val userId = authService.getUserId()
        val addBookmark = bookmarkFacade.toggleBookmark(userId, recipeId)
        val message = if (addBookmark) "북마크 추가 성공" else "북마크 삭제 성공"
        return ResponseEntity.ok().body(
            BookmarkToggleResponseDto(
                status = HttpStatus.OK.value(),
                message = message,
                success = true
            )
        )
    }
}