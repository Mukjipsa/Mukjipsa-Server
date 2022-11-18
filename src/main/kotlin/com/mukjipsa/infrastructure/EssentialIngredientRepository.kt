package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.EssentialIngredient
import org.springframework.data.jpa.repository.JpaRepository

interface EssentialIngredientRepository : JpaRepository<EssentialIngredient, Int> {
}