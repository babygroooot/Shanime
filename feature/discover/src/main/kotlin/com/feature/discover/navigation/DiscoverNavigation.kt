package com.feature.discover.navigation

import androidx.navigation.NavController

fun NavController.navigateToAnimeDetail(
    id: Long,
    title: String,
    image: String,
    score: String,
    members: Int,
    releasedYear: String,
    isAiring: Boolean,
    genres: String,
    synopsis: String,
    trailerVideoId: String,
) {
    navigate(
        route = DiscoverDestinations.AnimeDetail(
            id = id,
            title = title,
            image = image,
            score = score,
            members = members,
            releasedYear = releasedYear,
            isAiring = isAiring,
            genres = genres,
            synopsis = synopsis,
            trailerVideoId = trailerVideoId,
        ),
    )
}

fun NavController.navigateToSearchAnime() {
    navigate(
        route = DiscoverDestinations.Search,
    )
}
