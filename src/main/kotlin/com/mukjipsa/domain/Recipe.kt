package com.mukjipsa.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "recipe")
class Recipe(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int = 0,

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
        @JoinTable(name = "recipe_ingredient", //조인테이블명
                joinColumns = [JoinColumn(name = "recipe_id")],  //외래키
                inverseJoinColumns = [JoinColumn(name = "ingredient_id")]//반대 엔티티의 외래키
        )
        val ingredients: List<Ingredient> = listOf()
)