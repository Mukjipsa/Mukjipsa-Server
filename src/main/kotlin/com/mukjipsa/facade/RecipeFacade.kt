package com.mukjipsa.facade

import RecipeResponseDto
import com.mukjipsa.facade.dto.*
import com.mukjipsa.service.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RecipeFacade(
        private val recipeService: RecipeService,
        private val userIngredientService: UserIngredientService,
        private val recipeIngredientService: RecipeIngredientService,
        private val ingredientService: IngredientService,
        private val userService: UserService,
) {
    // 레시피 전체 조회
    fun getAllRecipe(): RecipeListSimpleResponseDto {
        val recipeList = recipeService.getAllRecipe()
        val recipeListDto = mutableListOf<RecipeSimpleDto>()
        recipeList.map {
            val ingredientIds = recipeIngredientService.getIngredientByRecipeId(it.id).map {
                it.ingredientId
            }
            val ingredientList = ingredientService.getIngredientByIdIn(ingredientIds)
            val recipeSimpleDto: RecipeSimpleDto = RecipeSimpleDto(
                    id = it.id,
                    content = it.content,
                    link = it.link,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    ingredients = ingredientList.map {
                        IngredientSimpleDto(
                                categoryType = it.category.name,
                                id = it.id,
                                name = it.name,
                        )
                    }
            )
            recipeListDto.add(recipeSimpleDto)
        }

        val recipeListSimple = recipeListDto.toList()

        return RecipeListSimpleResponseDto(
                data = recipeListSimple,
                message = "레시피 전체 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )
    }

    // 레시피 상세 조회
    fun getRecipe(userId: Int, recipeId: Int): RecipeResponseDto {
        val haveIngredientId = userService.getUserById(userId).get().ingredient.map {
            it.id
        }

        //recipe Id를 가진 ingredient 불러오기
        val ingredientIds = recipeIngredientService.getIngredientByRecipeId(recipeId).map {
            it.ingredientId
        }

        val ingredientList = ingredientService.getIngredientByIdIn(ingredientIds)

        val recipe = recipeService.getRecipe(recipeId).get()

        val recipeDto: RecipeDto = RecipeDto(
                id = recipe.id,
                content = recipe.content,
                link = recipe.link,
                thumbnail = recipe.thumbnail,
                title = recipe.title,
                createdAt = recipe.createdAt,
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
    fun getRecipeForMyIngredient(userId: Int): RecipeListResponseDto {

        // 내가 가진 ingredient id들
        val haveIngredientId = userService.getUserById(userId).get().ingredient.map {
            it.id
        }

        // 모든 recipe
        val recipeList = recipeService.getAllRecipe()
        val recipeListDto = mutableListOf<RecipeDto>()

        recipeList.map {
            var flag = false;

            // 해당 레시피의 모든 ingredient Id
            val ingredientIds = recipeIngredientService.getIngredientByRecipeId(it.id).map {
                it.ingredientId
            }

            ingredientIds.map {
                if (!haveIngredientId.contains(it)) { // 재료 중 하나라도 없다면
                    flag = true
                }
            }
            if (!flag) {
                // 해당 레시피의 모든 재료
                val ingredientList = ingredientService.getIngredientByIdIn(ingredientIds)

                val recipeDto: RecipeDto = RecipeDto(
                        id = it.id,
                        content = it.content,
                        link = it.link,
                        thumbnail = it.thumbnail,
                        title = it.title,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                        ingredients = ingredientList.map {
                            IngredientDto(
                                    categoryType = it.category.name,
                                    id = it.id,
                                    name = it.name,
                                    isHave = haveIngredientId.contains(it.id)
                            )
                        }
                )
                recipeListDto.add(recipeDto)
            }
        }
        val myRecipeList = recipeListDto.toList()

        return RecipeListResponseDto(
                data = myRecipeList,
                message = "나의 레시피 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )

    }


}