package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.UserIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface UserIngredientRepository : JpaRepository<UserIngredient, Int> {
}