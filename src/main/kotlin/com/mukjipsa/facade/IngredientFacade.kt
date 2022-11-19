package com.mukjipsa.facade

import com.mukjipsa.service.EssentialIngredientService
import com.mukjipsa.service.IngredientService
import com.mukjipsa.service.UserIngredientService
import com.mukjipsa.service.UserService
import org.springframework.stereotype.Service

@Service
class IngredientFacade(
    private val ingredientService: IngredientService,
    private val essentialIngredientService: EssentialIngredientService,
    private val userIngredientService: UserIngredientService,
    private val userService: UserService,
) {
}