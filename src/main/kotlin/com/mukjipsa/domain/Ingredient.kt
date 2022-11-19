package com.mukjipsa.domain

import java.time.LocalDateTime
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
    var id: Int,

    @Column(name = "name", nullable = false, length = 200)
    var name: String,

    @Column(name = "category_id", nullable = false)
    var categoryId: Int,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToOne
    @JoinColumn(name = "id")
    val category: Category,
)