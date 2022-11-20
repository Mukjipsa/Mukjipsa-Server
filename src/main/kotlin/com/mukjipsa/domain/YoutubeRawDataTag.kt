package com.mukjipsa.domain

import java.time.Instant
import java.util.UUID
import javax.persistence.*

@Entity
@Table(
    name = "youtube_raw_data_tag"
)
class YoutubeRawDataTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "tag", length = 50)
    var tag: String? = null,

    @Column(name = "youtube_raw_data_id")
    var youtubeRawDataId: Int? = null,

    @Column(name = "created_at")
    var createdAt: Instant? = null,
)