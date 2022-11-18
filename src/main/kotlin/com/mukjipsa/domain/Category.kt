package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(
    name = "category", uniqueConstraints = [
        UniqueConstraint(name = "category_id_uindex", columnNames = ["id"])
    ]
)
class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "name", nullable = false, length = 200)
    var name: String? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)