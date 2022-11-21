package com.mukjipsa.contoroller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipe")
class RecipeController(
        private val recipeFacade: RecipeFacade,
        private val authService: AuthService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getRecipeList(){
        // TODO 레시피 리스트.
        val userId = authService.getUserId()
        return bookmarkFacade.getRecipeList(userId)
    }

    @GetMapping("/{recipeId}")
    fun getRecipe(@PathVariable recipeId: String){
        // TODO 특정 레시피 조회.
        val userId = authService.getUserId()
        return bookmarkFacade.getRecipe(userId,recipeId)
    }

    @GetMapping("/my")
    fun getRecipeForMyIngredient(){
        // TODO 내가 가진 식재료로 만들 수 있는 레시피 리스트.
        val userId = authService.getUserId()
        return bookmarkFacade.getRecipeList(userId)
    }
}


