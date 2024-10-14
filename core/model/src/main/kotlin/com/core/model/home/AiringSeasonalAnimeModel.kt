package com.core.model.home

data class AiringSeasonalAnimeModel(
    val malId: Long,
    val url: String,
    val image: String,
    val title: String,
    val synopsis: String,
    val genres: List<AnimeMetadataModel>,
    val score: Float,
    val members: Int,
    val year: Int,
    val rating: String,
    val isAiring: Boolean,
    val trailerVideoId: String,
)
