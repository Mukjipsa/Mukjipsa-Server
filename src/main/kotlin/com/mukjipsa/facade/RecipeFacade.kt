package com.mukjipsa.facade

import RecipeResponseDto
import com.mukjipsa.domain.Ingredient
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.RecipeDto
import com.mukjipsa.facade.dto.RecipeListResponseDto
import com.mukjipsa.service.*
import org.springframework.stereotype.Service
import org.springframework.http.HttpStatus

@Service
class RecipeFacade(
        private val recipeService: RecipeService,
        private val userIngredientService: UserIngredientService,
        private val recipeIngredientService: RecipeIngredientService,
        private val ingredientService: IngredientService,
        private val userService: UserService,
) {
    // 레시피 전체 조회
//    fun getRecipeList(): RecipeListResponseDto {
//        val recipeList = recipeService.getAllRecipe()
//        return RecipeResponseDto(
//                data = ingredientsList.map {
//                    IngredientDto(
//                            id = it.id,
//                            categoryType = it.category.name,
//                            name = it.name,
//                    )
//                },
//                message = "내가 가진 식재료 조회 성공",
//                status = HttpStatus.OK.value(),
//                success = true
//        )
//    }

    // 레시피 상세 조회
    fun getRecipe(userId: Int, recipeId: Int):RecipeResponseDto{
        val haveIngredientId = userService.getUserById(userId).get().ingredient.map {
            it.id
        }
        println("11111")

        var ingredientIdsMutable: MutableList<Int> = mutableListOf<Int>()
        //recipe Id를 가진 ingredient 불러오기
        val ingredients = recipeIngredientService.getIngredientByRecipeId(recipeId).map{
            ingredientIdsMutable.add(it.ingredientId)
        }

        println("22222")

        var ingredientIds = ingredientIdsMutable.toList()
        var ingredientList = ingredientService.getIngredientByIdIn(ingredientIds)

        println("3333333")

        val recipe = recipeService.getRecipe(recipeId).get()
        println("44444")

        val recipeDto = RecipeDto(
                    content = recipe.content,
                    createdAt = recipe.createdAt,
                    id = recipe.id,
                    link = recipe.link,
                    thumbnail = recipe.thumbnail,
                    title = recipe.title,
                    updatedAt = recipe.updatedAt,
                    ingredients = ingredientList.map {
                        IngredientDto(
                                categoryType = it.category.name,
                                id = it.id,
                                name = it.name,
                                isHave = haveIngredientId.contains(it.id)
                                )
                    }
        )

        return RecipeResponseDto(
                data = recipeDto,
                message = "레시피 상세 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )

    }

    // 나의 재료 기반 레시피 조회
    fun getRecipeForMyIngredient(UserId: Int){

    }





}