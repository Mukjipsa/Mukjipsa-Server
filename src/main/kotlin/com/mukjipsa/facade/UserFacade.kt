package com.mukjipsa.facade

import com.mukjipsa.domain.Ingredient
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
        val haveIngredientId = userService.getUserById(userId).get().ingredient.map {
            it.id
        }
        val ingredientsList = ingredientService.getAllIngredient()
        return UserIngredientResponseDto(
            success = true,
            status = HttpStatus.OK.value(),
            message = "내가 가진 식재료 조회 성공",
            data = ingredientsList.map {
                IngredientDto(
                    id = it.id,
                    categoryType = it.category.name,
                    name = it.name,
                    isHave = haveIngredientId.contains(it.id)
                )
            }
        )
    }
}
