package com.feature.home.navigation

import androidx.navigation.NavController
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.TopAnimeModel
import com.core.model.home.TopMangaModel

fun NavController.navigateToBannerDetail(airingAnimeModel: AiringSeasonalAnimeModel) {
    navigate(
        HomeDestinations.AnimeDetail(
            id = airingAnimeModel.malId,
            title = airingAnimeModel.title,
            image = airingAnimeModel.image,
            score = airingAnimeModel.score,
            members = airingAnimeModel.members,
            releasedYear = airingAnimeModel.year.toString(),
            isAiring = airingAnimeModel.isAiring,
            genres = airingAnimeModel.genres.joinToString(separator = ", ") { it.name },
            synopsis = airingAnimeModel.synopsis,
            trailerVideoId = airingAnimeModel.trailerVideoId,
            navigateFromBanner = true,
        ),
    )
}

fun NavController.navigateToTopAnimeDetail(
    topAnimeModel: TopAnimeModel,
) {
    navigate(
        HomeDestinations.AnimeDetail(
            id = topAnimeModel.malId,
            title = topAnimeModel.title,
            image = topAnimeModel.image,
            score = topAnimeModel.score,
            members = topAnimeModel.members,
            releasedYear = topAnimeModel.year,
            isAiring = topAnimeModel.isAiring,
            genres = topAnimeModel.genres.joinToString(separator = ", ") { it.name },
            synopsis = topAnimeModel.synopsis,
            trailerVideoId = topAnimeModel.trailerVideoId,
            navigateFromBanner = false,
        ),
    )
}

fun NavController.navigateToTopMangaDetail(
    topMangaModel: TopMangaModel,
) {
    navigate(
        HomeDestinations.MangaDetail(
            id = topMangaModel.malId,
            title = topMangaModel.title,
            image = topMangaModel.image,
            score = topMangaModel.score,
            members = topMangaModel.members,
            authorName = topMangaModel.authorName,
            demographic = topMangaModel.demographic,
            isOnGoing = topMangaModel.isOnGoing,
            genres = topMangaModel.genres.joinToString(separator = ", ") { it.name },
            synopsis = topMangaModel.synopsis,
        ),
    )
}

fun NavController.navigateToTopHitAnime() {
    navigate(
        route = HomeDestinations.TopHitAnime,
    )
}

fun NavController.navigateToTopHitManga() {
    navigate(
        route = HomeDestinations.TopHitManga,
    )
}

fun NavController.navigateToSearchManga() {
    navigate(
        route = HomeDestinations.SearchManga,
    )
}
