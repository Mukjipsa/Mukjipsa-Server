package com.mukjipsa.service.impl

import com.mukjipsa.common.exception.EntityNotFoundException
import com.mukjipsa.domain.Recipe
import com.mukjipsa.infrastructure.RecipeRepository
import com.mukjipsa.service.RecipeService
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


@Service
class RecipeServiceImpl(
        private val recipeRepository: RecipeRepository,
        private val redisTemplate: RedisTemplate<String, String>,
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

    override fun saveSearchKeyword(userId: Int, keyword: String) {
        val key: String = "userId::" + userId;
        val stringStringSetOperations = redisTemplate.opsForSet()
        val size: Long? = stringStringSetOperations.size(key)
        if (size != null && size > 4) {
            stringStringSetOperations.pop(key)
        }
        stringStringSetOperations.add(key, keyword);
    }

    override fun getRecipeByKeyword(keyword: String): List<Recipe> {
        return recipeRepository.findByContentLike("%$keyword%")
    }
}