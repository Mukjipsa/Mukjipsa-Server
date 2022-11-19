package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.UserIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface UserIngredientRepository : JpaRepository<UserIngredient, Int> {
}