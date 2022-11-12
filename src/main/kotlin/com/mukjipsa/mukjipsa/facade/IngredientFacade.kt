package com.mukjipsa.mukjipsa.facade

import com.mukjipsa.mukjipsa.service.EssentialIngredientService
import com.mukjipsa.mukjipsa.service.IngredientService
import com.mukjipsa.mukjipsa.service.UserIngredientService
import com.mukjipsa.mukjipsa.service.UserService
import org.springframework.stereotype.Service

@Service
class IngredientFacade(
    private val ingredientService: IngredientService,
    private val essentialIngredientService: EssentialIngredientService,
    private val userIngredientService: UserIngredientService,
    private val userService: UserService,
) {
}