package com.mukjipsa.service.impl

import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.mukjipsa.domain.Recipe
import com.mukjipsa.domain.RecipeIngredient
import com.mukjipsa.domain.YoutubeRawData
import com.mukjipsa.domain.YoutubeRawDataTag
import com.mukjipsa.infrastructure.IngredientRepository
import com.mukjipsa.infrastructure.RecipeIngredientRepository
import com.mukjipsa.infrastructure.RecipeRepository
import com.mukjipsa.infrastructure.YoutubeRowDataRepository
import com.mukjipsa.service.SearchService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.transaction.Transactional


@Service
class SearchServiceImpl(
        private val youtubeRowDataRepository: YoutubeRowDataRepository,
        private val ingredientRepository: IngredientRepository,
        private val recipeRepository: RecipeRepository,
        private val recipeIngredientRepository: RecipeIngredientRepository,
) : SearchService {

    @Value("\${youtube.secretKey}")
    private lateinit var youtubeSecretKey: String

    /** Global instance of the HTTP transport.  */
    private val HTTP_TRANSPORT: HttpTransport = NetHttpTransport()

    /** Global instance of the JSON factory.  */
    private val JSON_FACTORY: JsonFactory = JacksonFactory()

    /**
     * Global instance of the max number of videos we want returned (50 = upper
     * limit per page).
     */
    private val NUMBER_OF_VIDEOS_RETURNED: Long = 1

    private val YOUTUBE_VIDEO_PREFIX = "https://www.youtube.com/watch?v="

    @Transactional
    override fun getYoutube(nextToken: String?) {
        val youtube =
                YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY) {}.setApplicationName("youtube-cmdline-search-sample").build()
        val search = youtube.search().list("id,snippet")
        val searchResultList = search.setKey(youtubeSecretKey)
                .setChannelId("UCC9pQY_uaBSa0WOpMNJHbEQ")
                .setOrder("date")
                .setPageToken(nextToken)
                .setType("video")
                .setFields("nextPageToken,pageInfo,items(id,snippet)")
                .setMaxResults(50)
                .execute()
        val items = searchResultList.items

        items.map {
            val snippet = it.snippet
            val videoId = it.id.videoId
            val videos = youtube.videos().list("snippet")
            val videoData = videos.setKey(youtubeSecretKey)
                    .setId(videoId)
                    .setFields("items(id,snippet)")
                    .execute()
                    .items.first()
            var tags = mutableListOf<YoutubeRawDataTag>()


            val youtubeRawData = YoutubeRawData(
                    channelId = "UCC9pQY_uaBSa0WOpMNJHbEQ",
                    videoId = videoId,
                    title = snippet.title,
                    thumbnails = videoData.snippet.thumbnails.default.url,
                    description = videoData.snippet.description,
                    publishedAt = Timestamp(snippet.publishedAt.value).toLocalDateTime(),
            )

            val id = youtubeRowDataRepository.save(youtubeRawData).id
            if (videoData.snippet.tags != null) {
                tags =
                        videoData.snippet.tags.map {
                            YoutubeRawDataTag(
                                    tag = it,
                                    youtubeRawDataId = id
                            )
                        }.toMutableList()
                youtubeRawData.addTags(tags)
            }
        }
        if (searchResultList.nextPageToken != null) {
            getYoutube(searchResultList.nextPageToken)
        }
    }

    override fun extractIngredient() {
        val ingredients = ingredientRepository.findAll()
        val youtubeRawData = youtubeRowDataRepository.findAll()
        val recipeEntityList = youtubeRawData.map {
            Recipe(
                    title = it.title,
                    thumbnail = it.thumbnails,
                    link = "$YOUTUBE_VIDEO_PREFIX${it.videoId}",
                    content = it.description,
            )
        }
        recipeRepository.saveAll(recipeEntityList)
        ingredients.map { ingredient ->
            if (!listOf("백미", "참쌀", "현미", "흑미", "즉석밥").contains(ingredient.name)) {
                val test = recipeRepository.findAllByContentLike("%${ingredient.name}%")
                recipeIngredientRepository.saveAll(test.map { recipe ->
                    RecipeIngredient(
                            recipeId = recipe.id,
                            ingredientId = ingredient.id
                    )
                })
            }
        }
    }
}
