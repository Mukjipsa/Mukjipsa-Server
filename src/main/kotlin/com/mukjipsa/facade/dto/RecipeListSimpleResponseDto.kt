package com.mukjipsa.facade.dto;

data class RecipeListSimpleResponseDto(
    val message: String, // 북마한 레시피 조회 성공
    val status: Int, // 200
    val success: Boolean, // true
    val data: List<RecipeSimpleDto>,
)