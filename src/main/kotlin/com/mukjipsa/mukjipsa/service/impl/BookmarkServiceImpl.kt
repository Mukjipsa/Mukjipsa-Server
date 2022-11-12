package com.mukjipsa.mukjipsa.service.impl

import com.mukjipsa.mukjipsa.infrastructure.BookmarkRepository
import com.mukjipsa.mukjipsa.service.BookmarkService
import org.springframework.stereotype.Service

@Service
class BookmarkServiceImpl(
    private val bookmarkRepository: BookmarkRepository,
): BookmarkService {
}