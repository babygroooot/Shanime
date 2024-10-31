package com.feature.discover.navigation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.core.designsystem.LocalNavAnimatedVisibilityScope
import com.feature.discover.ui.AnimeDetailScreen
import com.feature.discover.ui.DiscoverScreen
import com.feature.discover.ui.SearchAnimeScreen

fun NavGraphBuilder.discoverNavGraph(
    onSearchClick: () -> Unit,
    onDiscoverItemClick: (id: Long, title: String, image: String, score: String, members: Int, releasedYear: String, isAiring: Boolean, genres: String, synopsis: String, trailerVideoId: String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    navigation<DiscoverGraph>(startDestination = DiscoverDestinations.Discover) {
        composable<DiscoverDestinations.Discover>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            DiscoverScreen(
                onSearchClick = onSearchClick,
                onDiscoverItemClick = onDiscoverItemClick,
            )
        }

        composable<DiscoverDestinations.Search>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            CompositionLocalProvider(
                LocalNavAnimatedVisibilityScope provides this,
            ) {
                SearchAnimeScreen(
                    onDiscoverItemClick = onDiscoverItemClick,
                    onNavigateUp = onNavigateUp,
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
                onNavigateUp = onNavigateUp,
            )
        }
    }
}
