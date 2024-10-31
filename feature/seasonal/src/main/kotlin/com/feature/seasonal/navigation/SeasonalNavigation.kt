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
