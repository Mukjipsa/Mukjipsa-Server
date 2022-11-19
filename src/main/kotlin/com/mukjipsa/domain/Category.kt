package com.mukjipsa.domain

import java.time.LocalDateTime
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
    var id: Int = 0,

    @Column(name = "name", nullable = false, length = 200)
    var name: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)