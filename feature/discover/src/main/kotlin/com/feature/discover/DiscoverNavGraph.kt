package com.feature.discover

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
import com.feature.discover.ui.AnimeDetailScreen
import com.feature.discover.ui.DiscoverScreen
import com.feature.discover.ui.SearchAnimeScreen

fun NavGraphBuilder.discoverNavGraph(
    navController: NavController,
) {
    navigation<DiscoverGraph>(startDestination = DiscoverDestinations.Discover) {
        composable<DiscoverDestinations.Discover>(
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) },
            popEnterTransition = { fadeIn(animationSpec = tween()) },
            popExitTransition = { fadeOut(animationSpec = tween()) },
        ) {
            DiscoverScreen(
                navController = navController,
            )
        }

        composable<DiscoverDestinations.Search>(
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) },
            popEnterTransition = { fadeIn(animationSpec = tween()) },
            popExitTransition = { fadeOut(animationSpec = tween()) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                SearchAnimeScreen(
                    navController = navController,
                )
            }
        }

        composable<DiscoverDestinations.AnimeDetail> {
            val args = it.toRoute<DiscoverDestinations.AnimeDetail>()
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
