package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
    name = "bookmark", indexes = [
        Index(name = "bookmark_user_id_index", columnList = "user_id")
    ], uniqueConstraints = [
        UniqueConstraint(name = "bookmark_id_uindex", columnNames = ["id"])
    ]
)
class Bookmark (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Column(name = "user_id", nullable = false)
    var userId: Int? = null,

    @Column(name = "recipe_id", nullable = false)
    var recipeId: Int? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)