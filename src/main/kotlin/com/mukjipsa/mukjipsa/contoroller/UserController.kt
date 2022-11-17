package com.mukjipsa.mukjipsa.contoroller

import com.mukjipsa.mukjipsa.facade.UserFacade
import com.mukjipsa.mukjipsa.facade.dto.UserIngredientResponseDto
import com.mukjipsa.mukjipsa.service.AuthService
import com.mukjipsa.mukjipsa.service.BookmarkService
import com.mukjipsa.mukjipsa.service.UserService
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