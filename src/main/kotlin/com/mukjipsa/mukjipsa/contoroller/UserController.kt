package com.mukjipsa.mukjipsa.contoroller

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
    private val userService: UserService,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/ingredient")
    fun getMyIngredient(){
        // TODO 내가 가진 재료 리스트.
        log.info("my ingredient")
    }
}