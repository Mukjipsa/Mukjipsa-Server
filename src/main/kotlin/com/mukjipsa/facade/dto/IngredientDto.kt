package com.mukjipsa.facade.dto

data class IngredientDto(
    val categoryType: String, // 육류/해산물
    val id: Int, // 1
    val isHave: Boolean? = null, // true
    val name: String // 뒷다리살
)