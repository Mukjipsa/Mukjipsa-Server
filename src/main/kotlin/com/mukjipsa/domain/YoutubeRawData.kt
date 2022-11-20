package com.mukjipsa.domain

import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    name = "youtube_raw_data", uniqueConstraints = [
        UniqueConstraint(name = "youtube_raw_data_id_uindex", columnNames = ["id"])
    ]
)
class YoutubeRawData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "channel_id")
    var channelId: String? = null,

    @Column(name = "video_id", length = 50)
    var videoId: String? = null,

    @Column(name = "title")
    var title: String,

    @Lob
    @Column(name = "thumbnails")
    var thumbnails: String? = null,

    @Lob
    @Column(name = "description")
    var description: String? = null,

    @Column(name = "published_at")
    var publishedAt: LocalDateTime? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "youtube_raw_data_id")
    var tags: MutableList<YoutubeRawDataTag> = mutableListOf()
) {
    fun addTags(tagEntity: List<YoutubeRawDataTag>){
        tagEntity.forEach{
            tags.add(it)
        }
    }
}