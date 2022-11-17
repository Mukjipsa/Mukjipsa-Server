package com.mukjipsa.mukjipsa.infrastructure;

import com.mukjipsa.mukjipsa.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findBySsoId(ssoId: String): User?
}