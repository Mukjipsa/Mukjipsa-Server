package com.mukjipsa.contoroller;


import com.mukjipsa.facade.RecipeFacade;
import com.mukjipsa.facade.SearchFacade
import com.mukjipsa.facade.dto.SearchKeywordResponseDto
import com.mukjipsa.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/search")
public class SearchController(
        private val searchFacade:SearchFacade,
        private val authService:AuthService
) {
    private val log:Logger =LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllKeyword(): SearchKeywordResponseDto {
        // TODO 최근 검색어 전체 조회.
        val userId = authService.getUserId()
        return searchFacade.getMyKeyword(userId)
    }

    @DeleteMapping
    fun delKeyword(keywordId :Int){
        //TODO 특정 검색어 삭제
        searchFacade.delKeyword(keywordId)
    }



}