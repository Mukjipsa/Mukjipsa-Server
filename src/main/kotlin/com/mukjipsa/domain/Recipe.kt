package com.mukjipsa.mukjipsa.domain

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "recipe")
class Recipe (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "title", nullable = false, length = 200)
    var title: String? = null,

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
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,
)