package com.mukjipsa.contoroller.dto

data class EssentialIngredientResponseDto(
        val status: Int,
        val success: Boolean,
        val message: String,
        val data: List<SimpleIngredientDto>
)
