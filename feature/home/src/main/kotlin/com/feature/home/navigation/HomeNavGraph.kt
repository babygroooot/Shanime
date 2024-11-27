package com.feature.home.navigation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.designsystem.LocalNavAnimatedVisibilityScope
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.TopAnimeModel
import com.core.model.home.TopMangaModel
import com.feature.home.ui.AnimeDetailScreen
import com.feature.home.ui.HomeScreen
import com.feature.home.ui.MangaDetailScreen
import com.feature.home.ui.SearchMangaScreen
import com.feature.home.ui.TopHitAnimeScreen
import com.feature.home.ui.TopHitMangaScreen

fun NavGraphBuilder.homeNavGraph(
    onViewDetailClick: (airingAnimeModel: AiringSeasonalAnimeModel) -> Unit,
    onTopAnimeItemClick: (topAnimeModel: TopAnimeModel) -> Unit,
    onTopMangaItemClick: (topManga: TopMangaModel) -> Unit,
    onViewAllTopAnimeClick: () -> Unit,
    onViewAllTopMangaClick: () -> Unit,
    onSearchAnimeClick: () -> Unit,
    onSearchMangaClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    navigation<HomeGraph>(startDestination = HomeDestinations.Home) {
        composable<HomeDestinations.Home>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                HomeScreen(
                    onViewDetailClick = onViewDetailClick,
                    onTopAnimeItemClick = onTopAnimeItemClick,
                    onTopMangaItemClick = onTopMangaItemClick,
                    onViewAllTopAnimeClick = onViewAllTopAnimeClick,
                    onViewAllTopMangaClick = onViewAllTopMangaClick,
                    viewModel = hiltViewModel(),
                )
            }
        }

        composable<HomeDestinations.AnimeDetail>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                val args = it.toRoute<HomeDestinations.AnimeDetail>()
                AnimeDetailScreen(
                    id = args.id,
                    title = args.title,
                    image = args.image,
                    score = args.score,
                    members = args.members,
                    releasedYear = args.releasedYear,
                    isAiring = args.isAiring,
                    genres = args.genres,
                    synopsis = args.synopsis,
                    trailerVideoId = args.trailerVideoId,
                    navigateFromBanner = args.navigateFromBanner,
                    onNavigateUp = onNavigateUp,
                )
            }
        }

        composable<HomeDestinations.MangaDetail>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                val args = it.toRoute<HomeDestinations.MangaDetail>()
                MangaDetailScreen(
                    id = args.id,
                    title = args.title,
                    image = args.image,
                    score = args.score.toString(),
                    members = args.members,
                    authorName = args.authorName,
                    demographic = args.demographic,
                    genres = args.genres,
                    synopsis = args.synopsis,
                    onNavigateUp = onNavigateUp,
                )
            }
        }

        composable<HomeDestinations.TopHitAnime> {
            TopHitAnimeScreen(
                onTopAnimeItemClick = onTopAnimeItemClick,
                onSearchAnimeClick = onSearchAnimeClick,
                onNavigateUp = onNavigateUp,
            )
        }

        composable<HomeDestinations.TopHitManga> {
            TopHitMangaScreen(
                onTopMangaItemClick = onTopMangaItemClick,
                onSearchMangaClick = onSearchMangaClick,
                onNavigateUp = onNavigateUp,
            )
        }

        composable<HomeDestinations.SearchManga>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                SearchMangaScreen(
                    onTopMangaItemClick = onTopMangaItemClick,
                    onNavigateUp = onNavigateUp,
                )
            }
        }
    }
}
