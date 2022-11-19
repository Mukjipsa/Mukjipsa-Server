package com.mukjipsa.infrastructure;

import com.mukjipsa.domain.EssentialIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface EssentialIngredientRepository : JpaRepository<EssentialIngredient, Int> {
}