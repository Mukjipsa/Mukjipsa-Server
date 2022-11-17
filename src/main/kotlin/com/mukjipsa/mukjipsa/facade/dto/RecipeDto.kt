package com.mukjipsa.mukjipsa.facade.dto

import java.time.LocalDateTime

data class RecipeDto(
    val content: String? = null, // 같은건 없습니다. 그냥 사 드싶쇼.
    val createdAt: LocalDateTime? = null, // 2011-10-05T14:48:00.000Z
    val id: Int, // 1
    val ingredients: List<IngredientDto>? = null,
    val link: String? = null, // https://www.youtube.com/watch?v=TpPwI_Lo0YY
    val thumbnail: String? = null, // https://user-images.githubusercontent.com/51692363/199707408-e7feb782-6035-4528-a565-26e8af2d95ef.png
    val title: String? = null, // 누구나 해먹을 수 있는 자취요리
    val updatedAt: LocalDateTime? = null // 2011-10-05T14:48:00.000Z
)