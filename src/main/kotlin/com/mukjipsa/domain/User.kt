package com.mukjipsa.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
        name = "user", uniqueConstraints = [
    UniqueConstraint(name = "user_id_uindex", columnNames = ["id"])
]
)
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int = 0,

        @Column(name = "email")
        var email: String,

        @Column(name = "provider", nullable = false, length = 100)
        var provider: String,

        @Column(name = "sso_id", nullable = false)
        var ssoId: String,

        @Column(name="ingredients_round")
        var ingredientsRound: Int = 0,

        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now(),

        @OneToMany
        @JoinTable(name = "user_ingredient", //조인테이블명
                joinColumns = [JoinColumn(name = "user_id")],  //외래키
                inverseJoinColumns = [JoinColumn(name="ingredient_id")] //반대 엔티티의 외래키
        )
        var ingredient: MutableList<Ingredient> = mutableListOf()
)