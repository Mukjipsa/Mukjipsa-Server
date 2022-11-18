package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
    name = "ingredient", indexes = [
        Index(name = "ingredient_category_id_index", columnList = "category_id")
    ]
)
class Ingredient (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "name", nullable = false, length = 200)
    var name: String? = null,

    @Column(name = "category_id", nullable = false)
    var categoryId: Int? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)