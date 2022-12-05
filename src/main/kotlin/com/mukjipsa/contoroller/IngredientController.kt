package com.mukjipsa.contoroller

import com.mukjipsa.contoroller.dto.EssentialIngredientResponseDto
import com.mukjipsa.contoroller.dto.IngredientRequestDto
import com.mukjipsa.facade.IngredientFacade
import com.mukjipsa.facade.dto.IngredientAddResponseDto
import com.mukjipsa.facade.dto.IngredientResponseDto
import com.mukjipsa.facade.dto.IngredientUpdateResponseDto
import com.mukjipsa.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ingredient")
class IngredientController(
    private val ingredientFacade: IngredientFacade,
    private val authService: AuthService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/essential")
    fun getEssentialIngredientList(): EssentialIngredientResponseDto {
        return ingredientFacade.getEssentialIngredient()
    }

    @GetMapping
    fun getIngredientList() : IngredientResponseDto {
        // TODO 식재료 리스트 전달.
        val userId = authService.getUserId()
        return ingredientFacade.getUserIngredient(userId)
    }

    @PostMapping
    fun addIngredient(@RequestBody ingredientRequestDto: IngredientRequestDto): IngredientAddResponseDto {
        val userId = authService.getUserId()
        return ingredientFacade.addIngredient(userId, ingredientRequestDto.ingredients)
    }

    @PutMapping
    fun updateIngredient(@RequestBody ingredientRequestDto: IngredientRequestDto): IngredientUpdateResponseDto {
        val userId = authService.getUserId()
        return ingredientFacade.updateIngredient(userId, ingredientRequestDto.ingredients)
    }
}