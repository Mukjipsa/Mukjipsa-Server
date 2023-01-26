package com.mukjipsa.facade

import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.UserIngredientResponseDto
import com.mukjipsa.service.IngredientService
import com.mukjipsa.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserFacade(
        private val userService: UserService,
        private val ingredientService: IngredientService,
) {
    fun getUserIngredients(userId: Int): UserIngredientResponseDto {
        val haveIngredientId = userService.getUserById(userId).ingredient.map {
            it.id
        }
        val ingredientsList = ingredientService.getAllIngredient()
        return UserIngredientResponseDto(
                data = ingredientsList.map {
                    IngredientDto(
                        categoryType = it.category.name,
                        id = it.id,
                        isHave = haveIngredientId.contains(it.id),
                        name = it.name,
                        categoryId = it.category.id
                    )
                },
                message = "내가 가진 식재료 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }
}
