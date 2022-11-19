package com.mukjipsa.service.impl

import com.mukjipsa.infrastructure.CategoryRepository
import com.mukjipsa.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
}