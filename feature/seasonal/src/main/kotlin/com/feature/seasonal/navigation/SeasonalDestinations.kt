package com.feature.seasonal.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SeasonalDestinations {
    @Serializable
    data object Seasonal : SeasonalDestinations

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
    ) : SeasonalDestinations
}

@Serializable
data object SeasonalGraph
