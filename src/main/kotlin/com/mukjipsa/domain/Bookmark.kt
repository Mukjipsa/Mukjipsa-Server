package com.mukjipsa.domain

import java.time.LocalDateTime
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
    var userId: Int,

    @Column(name = "recipe_id", nullable = false)
    var recipeId: Int,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)