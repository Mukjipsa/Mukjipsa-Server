package com.mukjipsa.service.impl

import com.mukjipsa.common.exception.EntityNotFoundException
import com.mukjipsa.domain.Recipe
import com.mukjipsa.infrastructure.RecipeRepository
import com.mukjipsa.service.RecipeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl(
        private val recipeRepository: RecipeRepository
) : RecipeService {

    override fun getRecipeByIdIn(recipeIds: List<Int>): List<Recipe> {
        return recipeRepository.findByIdIn(recipeIds)
    }

    override fun getAllRecipe(): List<Recipe> {
        return recipeRepository.findAll()
    }


    override fun getRecipe(recipeId: Int): Recipe {
        return recipeRepository.findById(recipeId).orElseThrow{throw EntityNotFoundException(message = "$recipeId is not found")}
    }

    override fun getRecipeByKeyword(keyword: String): List<Recipe> {
        val param = "%$keyword%"
        LoggerFactory.getLogger(javaClass).info("[TEST] $param")
        return recipeRepository.findByContentLike(param)
    }
}