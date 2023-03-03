package com.mukjipsa.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "search_keyword")
class SearchKeyword(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int = 0,

        @Column(name = "keyword", nullable = false, length = 200)
        var keyword: String,

        @Column(name = "user_id", nullable = false)
        var userId: Int,


        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now(),
)