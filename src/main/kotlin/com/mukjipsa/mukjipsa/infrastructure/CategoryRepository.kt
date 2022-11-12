package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int> {
}