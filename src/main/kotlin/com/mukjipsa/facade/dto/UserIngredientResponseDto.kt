package com.mukjipsa.facade.dto

data class UserIngredientResponseDto(
    val status: Int, // 200
    val message: String,
    val success: Boolean, // true
    val data: List<IngredientDto>,
)
