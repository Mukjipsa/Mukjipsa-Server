package com.mukjipsa.infrastructure

import com.mukjipsa.domain.YoutubeRawData
import org.springframework.data.jpa.repository.JpaRepository

interface YoutubeRowDataRepository : JpaRepository<YoutubeRawData, Int> {
    fun findAllByDescriptionLike(ingredient: String): List<YoutubeRawData>
}