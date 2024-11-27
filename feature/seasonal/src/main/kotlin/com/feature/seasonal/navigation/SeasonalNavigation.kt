package com.feature.seasonal.navigation

import androidx.navigation.NavController
import com.core.model.home.AiringSeasonalAnimeModel

fun NavController.navigateToSeasonalAnimeDetail(
    airingSeasonalAnimeModel: AiringSeasonalAnimeModel,
) {
    navigate(
        route = SeasonalDestinations.AnimeDetail(
            id = airingSeasonalAnimeModel.malId,
            title = airingSeasonalAnimeModel.title,
            image = airingSeasonalAnimeModel.image,
            score = airingSeasonalAnimeModel.score,
            members = airingSeasonalAnimeModel.members,
            releasedYear = airingSeasonalAnimeModel.year.toString(),
            isAiring = airingSeasonalAnimeModel.isAiring,
            genres = airingSeasonalAnimeModel.genres.joinToString(separator = ", ") { it.name },
            synopsis = airingSeasonalAnimeModel.synopsis,
            trailerVideoId = airingSeasonalAnimeModel.trailerVideoId,
        ),
    )
}

fun NavController.navigateToSeasonalAnimeDetail(
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
        route = SeasonalDestinations.AnimeDetail(
            id = id,
            title = title,
            image = image,
            score = score,
            members = members,
            releasedYear = releasedYear.toString(),
            isAiring = isAiring,
            genres = genres,
            synopsis = synopsis,
            trailerVideoId = trailerVideoId,
        ),
    )
}

fun NavController.navigateToArchive(
    year: String,
    season: String,
) {
    navigate(
        route = SeasonalDestinations.Archive(
            year = year,
            season = season,
        ),
    )
}
