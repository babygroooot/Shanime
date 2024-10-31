package com.feature.discover.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface DiscoverDestinations {

    @Serializable
    data object Discover : DiscoverDestinations

    @Serializable
    data object Search : DiscoverDestinations

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
    ) : DiscoverDestinations
}

@Serializable
data object DiscoverGraph
