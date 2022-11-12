package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
    name = "recipe_ingredient", indexes = [
        Index(name = "recipe_ingredient_ingredient_id_index", columnList = "ingredient_id"),
        Index(name = "recipe_ingredient_recipe_id_index", columnList = "recipe_id")
    ], uniqueConstraints = [
        UniqueConstraint(name = "recipe_ingredient_id_uindex", columnNames = ["id"])
    ]
)
class RecipeIngredient (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "recipe_id", nullable = false)
    var recipeId: Int? = null,

    @Column(name = "ingredient_id", nullable = false)
    var ingredientId: Int? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)