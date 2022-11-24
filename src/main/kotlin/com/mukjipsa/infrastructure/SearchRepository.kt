package com.mukjipsa.infrastructure

import com.mukjipsa.domain.Recipe
import com.mukjipsa.domain.SearchKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface SearchRepository: JpaRepository<SearchKeyword, Int> {

    fun findAllByUserId(userId:Int): List<SearchKeyword>
}