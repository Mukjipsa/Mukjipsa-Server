package com.mukjipsa.facade

import com.mukjipsa.facade.dto.SearchKeywordDto
import com.mukjipsa.facade.dto.SearchKeywordResponseDto
import com.mukjipsa.service.SearchService
import org.springframework.data.redis.core.ListOperations
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SearchFacade(
        private val searchService: SearchService
) {
    fun getMyKeywords(userId: Int): SearchKeywordResponseDto {
        val keywordList: List<String>? = searchService.getMyKeywords(userId)

        return SearchKeywordResponseDto(
                data = keywordList,
                message = "최근 검색어 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )

    }

    fun deleteKeyword(userId: Int, keyword: String) {
        searchService.deleteKeyword(userId, keyword)
    }
}