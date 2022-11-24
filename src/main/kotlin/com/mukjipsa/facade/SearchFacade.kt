package com.mukjipsa.facade

import com.mukjipsa.domain.SearchKeyword
import com.mukjipsa.facade.dto.*
import com.mukjipsa.service.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SearchFacade(
        private val searchService: SearchService
) {
    fun getMyKeywords(userId: Int):SearchKeywordResponseDto {
        val keywordList :List<SearchKeyword> = searchService.getMyKeywords(userId)
        val searchKeywordListDto = mutableListOf<SearchKeywordDto>()

        keywordList.map {
            val keywordDto: SearchKeywordDto = SearchKeywordDto(
                    id = it.id,
                    userId = it.userId,
                    keyword = it.keyword,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
            )
            searchKeywordListDto.add(keywordDto)
        }

        val keywords = searchKeywordListDto.toList()

        return SearchKeywordResponseDto(
                data = keywords,
                message = "최근 검색어 조회 성공",
                status = HttpStatus.OK.value(),
                success = true
        )


    }

    fun deleteKeyword(keywordId: Int) {
        val keyword :SearchKeyword = searchService.getKeyword(keywordId).get()
        if(keyword != null){
            searchService.deleteKeyword(keywordId)
        }
    }
}