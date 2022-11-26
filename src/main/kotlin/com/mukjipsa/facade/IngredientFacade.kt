package com.mukjipsa.facade

import com.mukjipsa.facade.dto.IngredientResponseDto
import com.mukjipsa.service.EssentialIngredientService
import com.mukjipsa.service.IngredientService
import com.mukjipsa.service.UserIngredientService
import com.mukjipsa.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class IngredientFacade(
        private val ingredientService: IngredientService,
        private val essentialIngredientService: EssentialIngredientService,
        private val userIngredientService: UserIngredientService,
        private val userService: UserService,
) {
    fun getUserIngredient(userId: Int): IngredientResponseDto {
        val ingredientIds = userIngredientService.getIngredientByUserId(userId).map { it.ingredientId }
        val ingredientList = ingredientService.getIngredientByIdIn(ingredientIds)
        return IngredientResponseDto(
                data = ingredientList,
                message = "식재료 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }
}