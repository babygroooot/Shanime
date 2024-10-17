package com.feature.seasonal

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.core.designsystem.LocalNavAnimatedVisibilityScope
import com.feature.seasonal.ui.AnimeDetailScreen
import com.feature.seasonal.ui.SeasonalScreen

fun NavGraphBuilder.seasonalNavGraph(
    navController: NavController,
) {
    navigation<SeasonalGraph>(startDestination = SeasonalDestinations.Seasonal) {
        composable<SeasonalDestinations.Seasonal>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                SeasonalScreen(
                    navController = navController,
                )
            }
        }

        composable<SeasonalDestinations.AnimeDetail>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                val args = it.toRoute<SeasonalDestinations.AnimeDetail>()
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
                    navController = navController,
                )
            }
        }
    }
}
