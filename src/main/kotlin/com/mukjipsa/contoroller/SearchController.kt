package com.mukjipsa.contoroller;


import com.mukjipsa.facade.SearchFacade
import com.mukjipsa.facade.dto.SearchKeywordResponseDto
import com.mukjipsa.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/search")
public class SearchController(
        private val searchFacade: SearchFacade,
        private val authService: AuthService
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("")
    fun getAllKeywords(): SearchKeywordResponseDto {
        // TODO 최근 검색어 전체 조회.
        val userId = authService.getUserId()
        return searchFacade.getMyKeywords(userId)
    }

    @DeleteMapping("")
    fun deleteKeyword(@RequestParam("keyword") keyword: String) {
        //TODO 특정 검색어 삭제
        val userId = authService.getUserId()
        searchFacade.deleteKeyword(userId, keyword)
    }


}