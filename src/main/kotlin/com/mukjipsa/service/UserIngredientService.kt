package com.mukjipsa.service

import com.mukjipsa.domain.UserIngredient

interface UserIngredientService {
    fun getIngredientByUserId(userId: Int): List<UserIngredient>
}