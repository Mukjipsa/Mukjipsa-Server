package com.mukjipsa.mukjipsa.facade.dto

data class UserIngredientResponseDto(
    val data: List<IngredientDto>,
    val message: String,
    val status: Int, // 200
    val success: Boolean // true
)
