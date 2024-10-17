package com.feature.home

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.designsystem.LocalNavAnimatedVisibilityScope
import com.feature.home.ui.AnimeDetailScreen
import com.feature.home.ui.HomeScreen
import com.feature.home.ui.MangaDetailScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
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
                    navController = navController,
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
                    navController = navController,
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
                    isOnGoing = args.isOnGoing,
                    genres = args.genres,
                    synopsis = args.synopsis,
                    navController = navController,
                )
            }
        }
    }
}
