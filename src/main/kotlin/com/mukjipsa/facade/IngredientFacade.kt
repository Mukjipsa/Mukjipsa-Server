package com.mukjipsa.facade

import com.mukjipsa.contoroller.dto.EssentialIngredientResponseDto
import com.mukjipsa.contoroller.dto.SimpleIngredientDto
import com.mukjipsa.facade.dto.IngredientAddResponseDto
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.IngredientResponseDto
import com.mukjipsa.facade.dto.IngredientUpdateResponseDto
import com.mukjipsa.service.EssentialIngredientService
import com.mukjipsa.service.IngredientService
import com.mukjipsa.service.UserIngredientService
import com.mukjipsa.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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
                data = ingredientList.map {
                    IngredientDto(
                            categoryType = it.category.name,
                            id = it.id,
                            isHave = true,
                            name = it.name
                    )
                },
                message = "식재료 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }

    @Transactional
    fun addIngredient(userId: Int, ingredientIds: List<Int>): IngredientAddResponseDto {
        val user = userService.getUserById(userId)
        val ingredients = ingredientService.getIngredientByIdIn(ingredientIds)
        user.ingredient.addAll(ingredients)
        return IngredientAddResponseDto(
                message = "식재료 추가 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }

    @Transactional
    fun updateIngredient(userId: Int, ingredientIds: List<Int>): IngredientUpdateResponseDto {
        val user = userService.getUserById(userId)
        val ingredients = ingredientService.getIngredientByIdIn(ingredientIds)
        user.ingredient = ingredients.toMutableList()
        return IngredientUpdateResponseDto(
                message = "식재료 업데이트 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }

    fun getEssentialIngredient(): EssentialIngredientResponseDto {
        val essentialIngredientIds: List<Int> = essentialIngredientService.getAllIds()
        val ingredients = ingredientService.getIngredientByIdIn(essentialIngredientIds)
        return EssentialIngredientResponseDto(
                status = 200,
                success = true,
                message = "필수 식재료 조회 성공",
                data = ingredients.map {
                    SimpleIngredientDto(
                            id = it.id,
                            name = it.name
                    )
                }
        )
    }
}