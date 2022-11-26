package com.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
        name = "user_ingredient", indexes = [
    Index(name = "user_ingredient_user_id_index", columnList = "user_id")
]
)
class UserIngredient(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int,

        @Column(name = "user_id", nullable = false)
        var userId: Int,

        @Column(name = "ingredient_id", nullable = false)
        var ingredientId: Int,

        @Column(name = "created_at")
        var createdAt: Instant? = null,

        @Column(name = "updated_at")
        var updatedAt: Instant? = null,
)