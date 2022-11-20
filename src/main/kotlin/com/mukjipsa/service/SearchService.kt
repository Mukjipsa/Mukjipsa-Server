package com.mukjipsa.service

interface SearchService {
    fun getYoutube(nextToken: String?)
    fun extractIngredient()
}