package com.mukjipsa.facade

import RecipeResponseDto
import com.mukjipsa.facade.dto.*
import com.mukjipsa.service.BookmarkService
import com.mukjipsa.service.IngredientService
import com.mukjipsa.service.RecipeService
import com.mukjipsa.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RecipeFacade(
        private val recipeService: RecipeService,
        private val userService: UserService,
        private val ingredientService: IngredientService,
        private val bookmarkService: BookmarkService,
) {
    // 레시피 전체 조회
    fun getAllRecipe(): RecipeListSimpleResponseDto {
        val recipeList = recipeService.getAllRecipe()
        val recipeListDto = mutableListOf<RecipeSimpleDto>()
        recipeList.map {
            val recipeSimpleDto: RecipeSimpleDto = RecipeSimpleDto(
                    id = it.id,
                    content = it.content,
                    link = it.link,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    ingredients = it.ingredients.map {
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
        val haveIngredientId = userService.getUserById(userId).ingredient.map {
            it.id
        }

        val recipe = recipeService.getRecipe(recipeId)
        val isBookmarked = bookmarkService.getBookmark(userId, recipeId)

        val recipeDto: RecipeDto = RecipeDto(
                id = recipe.id,
                content = recipe.content,
                link = recipe.link,
                thumbnail = recipe.thumbnail,
                title = recipe.title,
                createdAt = recipe.createdAt,
                updatedAt = recipe.updatedAt,
                ingredients = recipe.ingredients.map {
                    IngredientDto(
                        categoryType = it.category.name,
                        id = it.id,
                        isHave = haveIngredientId.contains(it.id),
                        name = it.name,
                        categoryId = it.category.id
                    )
                },
                isBookmarked = isBookmarked != null
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
        val haveIngredientId = userService.getUserById(userId).ingredient.map {
            it.id
        }

        // 모든 recipe
        val recipeList = recipeService.getAllRecipe()
        val recipeListDto = mutableListOf<RecipeDto>()
        recipeList.map {
            var flag = false;

            // 해당 레시피의 모든 ingredient Id
            val ingredientIds = it.ingredients.map { it.id }

            ingredientIds.map {
                if (!haveIngredientId.contains(it)) { // 재료 중 하나라도 없다면
                    flag = true
                }
            }
            if (!flag) {
                val recipeDto: RecipeDto = RecipeDto(
                    content = it.content,
                    createdAt = it.createdAt,
                    id = it.id,
                    ingredients = it.ingredients.map {
                        IngredientDto(
                            categoryType = it.category.name,
                            id = it.id,
                            isHave = haveIngredientId.contains(it.id),
                            name = it.name,
                            categoryId = it.category.id
                        )
                    },
                    link = it.link,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    updatedAt = it.updatedAt,
                    isBookmarked = bookmarkService.getBookmark(userId, it.id) != null
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

    fun getSearchRecipe(userId: Int, keyword: String): RecipeListResponseDto {
        recipeService.saveSearchKeyword(userId, keyword)

        val data = recipeService.getRecipeByKeyword(keyword).map {
            RecipeDto(
                content = it.content,
                createdAt = it.createdAt,
                id = it.id,
                ingredients = it.ingredients.map {
                    IngredientDto(
                        categoryType = it.category.name,
                        id = it.id,
                        name = it.name,
                        categoryId = it.category.id,
                    )
                },
                link = it.link,
                thumbnail = it.thumbnail,
                title = it.title,
                updatedAt = it.updatedAt,
                isBookmarked = bookmarkService.getBookmark(userId, it.id) != null
            )
        }
        return RecipeListResponseDto(
            message = "레시피 조회 성공",
            status = HttpStatus.OK.value(),
            success = true,
            data = data,
        )
    }
}