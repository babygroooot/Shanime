package com.feature.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeDestinations {

    @Serializable
    data object Home : HomeDestinations

    @Serializable
    data class AnimeDetail(
        val id: Long,
        val title: String,
        val image: String,
        val score: String,
        val members: Int,
        val releasedYear: String,
        val isAiring: Boolean,
        val genres: String,
        val synopsis: String,
        val trailerVideoId: String,
        val navigateFromBanner: Boolean,
    ) : HomeDestinations

    @Serializable
    data class MangaDetail(
        val id: Long,
        val image: String,
        val title: String,
        val synopsis: String,
        val score: String,
        val genres: String,
        val authorName: String,
        val isOnGoing: Boolean,
        val members: Int,
        val demographic: String,
    ) : HomeDestinations

    @Serializable
    data object TopHitAnime : HomeDestinations

    @Serializable
    data object TopHitManga : HomeDestinations

    @Serializable
    data object SearchManga : HomeDestinations
}

@Serializable
data object HomeGraph
