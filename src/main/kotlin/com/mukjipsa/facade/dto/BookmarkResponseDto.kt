package com.mukjipsa.facade.dto

data class BookmarkResponseDto(
        val data: List<RecipeDto>,
        val message: String, // 북마한 레시피 조회 성공
        val status: Int, // 200
        val success: Boolean // true
)