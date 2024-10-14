package com.core.data.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
data class TopMangaDTO(
    @SerialName("mal_id")
    val malId: Long,

    val url: String,
    val images: Map<String, ImageTypeDTO>,
    val approved: Boolean,
    val titles: List<TitlesDTO>,
    val title: String,

    @SerialName("title_english")
    val titleEnglish: String,

    @SerialName("title_japanese")
    val titleJapanese: String,

    @SerialName("title_synonyms")
    val titleSynonyms: List<String>,

    val type: String,
    val chapters: Int? = null,
    val volumes: Int? = null,
    val status: String,
    val score: Float,
    val publishing: Boolean,

    @SerialName("scored_by")
    val scoredBy: Long,

    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String,
    val background: String,
    val authors: List<AnimeMetadataDTO>,
    val genres: List<AnimeMetadataDTO>,

    @SerialName("explicit_genres")
    val explicitGenres: JsonArray,

    val themes: List<AnimeMetadataDTO>,
    val demographics: List<AnimeMetadataDTO>,
)
