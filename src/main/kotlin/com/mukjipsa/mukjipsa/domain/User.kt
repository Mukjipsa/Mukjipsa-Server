package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
    name = "user", uniqueConstraints = [
        UniqueConstraint(name = "user_id_uindex", columnNames = ["id"])
    ]
)
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "provider", nullable = false, length = 100)
    var provider: String? = null,

    @Column(name = "sso_id", nullable = false)
    var ssoId: String? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)