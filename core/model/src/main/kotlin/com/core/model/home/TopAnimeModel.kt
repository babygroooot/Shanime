package com.core.model.home

data class TopAnimeModel(
    val malId: Long,
    val url: String,
    val image: String,
    val title: String,
    val synopsis: String,
    val score: String,
    val genres: List<AnimeMetadataModel>,
    val year: String,
    val season: String,
    val isAiring: Boolean,
    val members: Int,
    val trailerVideoId: String,
    val rating: String,
)
