package com.mukjipsa.mukjipsa.facade

import com.mukjipsa.mukjipsa.service.BookmarkService
import org.springframework.stereotype.Service

@Service
class BookmarkFacade(
    private val bookmarkService: BookmarkService,
) {
}