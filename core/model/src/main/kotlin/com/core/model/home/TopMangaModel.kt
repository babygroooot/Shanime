package com.core.model.home

data class TopMangaModel(
    val malId: Long,
    val url: String,
    val image: String,
    val title: String,
    val synopsis: String,
    val score: String,
    val genres: List<AnimeMetadataModel>,
    val authorName: String,
    val isOnGoing: Boolean,
    val members: Int,
    val demographic: String,
)
