package com.mukjipsa.service

//import com.mukjipsa.domain.SearchKeyword
import java.util.*

interface SearchService {
    fun getYoutube(nextToken: String?)
    fun extractIngredient()
    fun getMyKeywords(userId: Int): List<String>?
    fun deleteKeyword(userId: Int, keyword: String)
}