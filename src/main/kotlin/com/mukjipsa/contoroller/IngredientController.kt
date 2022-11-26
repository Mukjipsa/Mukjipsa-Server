package com.mukjipsa.contoroller

import com.mukjipsa.facade.IngredientFacade
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ingredient")
class IngredientController(
        private val ingredientFacade: IngredientFacade,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/essential")
    fun getEssentialIngredientList() {
        // TODO 필수 식재료 리스트.
        log.info("essential ingredient list")
    }

    @GetMapping
    fun getIngredientList() {
        // TODO 식재료 리스트 전달.
        log.info("ingredient list")
    }

    @PostMapping
    fun addIngredient(ingredientIds: List<Int>) {
        // TODO 유저에게 식재료 추가
        log.info("add ingredient to user")
    }

    @PutMapping
    fun updateIngredient(ingredientIds: List<Int>) {
        // TODO 유저가 가진 식재료 업데이트
        log.info("update ingredient to user")
    }
}