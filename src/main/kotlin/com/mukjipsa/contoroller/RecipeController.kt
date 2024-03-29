package com.mukjipsa.contoroller

import RecipeResponseDto
import com.mukjipsa.common.exception.BusinessException
import com.mukjipsa.common.exception.EntityNotFoundException
import com.mukjipsa.common.exception.response.ErrorCode
import com.mukjipsa.common.exception.response.ResponseError
import com.mukjipsa.facade.RecipeFacade
import com.mukjipsa.facade.dto.RecipeListResponseDto
import com.mukjipsa.facade.dto.RecipeListSimpleResponseDto
import com.mukjipsa.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException

@RestController
@RequestMapping("/recipe")
class RecipeController(
        private val recipeFacade: RecipeFacade,
        private val authService: AuthService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllRecipe(): RecipeListSimpleResponseDto {
        // TODO 레시피 리스트.
        return recipeFacade.getAllRecipe()
    }

    @GetMapping("/{recipeId}")
    fun getRecipe(@PathVariable recipeId: Int): RecipeResponseDto {
        // TODO 특정 레시피 조회.
        val userId = authService.getUserId()
        return recipeFacade.getRecipe(userId, recipeId)
    }

    @GetMapping("/my")
    fun getRecipeForMyIngredient(): RecipeListResponseDto {
        // TODO 내가 가진 식재료로 만들 수 있는 레시피 리스트.
        val userId = authService.getUserId()
        return recipeFacade.getRecipeForMyIngredient(userId)
    }

    @GetMapping("/search")
    fun getSearchRecipe(@RequestParam("keyword") keyword: String): RecipeListResponseDto{
        val userId = authService.getUserId()
        return recipeFacade.getSearchRecipe(userId, keyword)
    }
}


