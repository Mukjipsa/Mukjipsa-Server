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

        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now(),

        @OneToMany
        @JoinColumn(name = "id")
        val ingredient: MutableList<Ingredient> = mutableListOf()
)