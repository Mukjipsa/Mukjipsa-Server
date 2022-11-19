package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int> {
}