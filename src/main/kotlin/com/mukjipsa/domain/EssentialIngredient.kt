package com.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
        name = "essential_ingredient", uniqueConstraints = [
    UniqueConstraint(name = "essential_ingredient_id_uindex", columnNames = ["id"])
]
)
class EssentialIngredient(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "ingredient_id", nullable = false)
        var ingredientId: Int? = null,

        @Column(name = "created_at")
        var createdAt: Instant? = null,

        @Column(name = "updated_at")
        var updatedAt: Instant? = null,
)