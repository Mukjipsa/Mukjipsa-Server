package com.mukjipsa.contoroller

import com.mukjipsa.facade.UserFacade
import com.mukjipsa.facade.dto.UserIngredientResponseDto
import com.mukjipsa.service.AuthService
import com.mukjipsa.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userFacade: UserFacade,
    private val authService: AuthService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/ingredient")
    fun getMyIngredient(): UserIngredientResponseDto {
        val userId = authService.getUserId()
        return userFacade.getUserIngredients(userId)
    }
}