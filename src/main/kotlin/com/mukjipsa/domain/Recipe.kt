package com.mukjipsa.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "recipe")
class Recipe (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int,

    @Column(name = "title", nullable = false, length = 200)
    var title: String,

    @Lob
    @Column(name = "thumbnail")
    var thumbnail: String? = null,

    @Lob
    @Column(name = "link")
    var link: String? = null,

    @Lob
    @Column(name = "content")
    var content: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany
    @JoinColumn(name = "ingredient_id")
    val ingredients: MutableList<Ingredient> = mutableListOf()
)