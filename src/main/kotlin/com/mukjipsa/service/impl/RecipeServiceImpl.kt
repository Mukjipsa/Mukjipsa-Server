package com.mukjipsa.service.impl

import com.mukjipsa.domain.Recipe
import com.mukjipsa.facade.dto.IngredientDto
import com.mukjipsa.facade.dto.RecipeDto
import com.mukjipsa.infrastructure.RecipeRepository
import com.mukjipsa.service.RecipeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecipeServiceImpl(
        private val recipeRepository: RecipeRepository
) : RecipeService {
    override fun getRecipeByIdIn(recipeIds: List<Int>): List<RecipeDto> {
        return recipeRepository.findByIdIn(recipeIds).map {
            RecipeDto(
                    content = it.content,
                    createdAt = it.createdAt,
                    id = it.id,
                    link = it.link,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    updatedAt = it.updatedAt,
                    ingredients = it.ingredients.map {
                        IngredientDto(
                                categoryType = it.category.name,
                                id = it.id,
                                name = it.name,
                                isHave = null
                        )
                    }
            )
        }
    }

    override fun getAllRecipe(): List<Recipe> {
        return recipeRepository.findAll()
    }


    override fun getRecipe(recipeId: Int): Optional<Recipe> {
        return recipeRepository.findById(recipeId)
    }


}