package com.babygroot.shanime.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.babygroot.shanime.TopLevelDestination
import com.core.designsystem.LocalSharedTransitionScope
import com.core.designsystem.ShanimeTheme
import com.feature.discover.navigation.discoverNavGraph
import com.feature.discover.navigation.navigateToAnimeDetail
import com.feature.discover.navigation.navigateToSearchAnime
import com.feature.home.navigation.HomeGraph
import com.feature.home.navigation.homeNavGraph
import com.feature.home.navigation.navigateToBannerDetail
import com.feature.home.navigation.navigateToSearchManga
import com.feature.home.navigation.navigateToTopAnimeDetail
import com.feature.home.navigation.navigateToTopHitAnime
import com.feature.home.navigation.navigateToTopHitManga
import com.feature.home.navigation.navigateToTopMangaDetail
import com.feature.seasonal.navigation.navigateToArchive
import com.feature.seasonal.navigation.navigateToSeasonalAnimeDetail
import com.feature.seasonal.navigation.seasonalNavGraph
import com.feature.setting.navigation.navigateToAboutUs
import com.feature.setting.navigation.navigateToFontSize
import com.feature.setting.navigation.navigateToTermAndCondition
import com.feature.setting.navigation.settingNavGraph

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentBackstack by navController.currentBackStackEntryAsState()
    val currentDestination by remember {
        derivedStateOf {
            currentBackstack?.destination
        }
    }
    val bottomBarDestinations = remember {
        listOf(
            TopLevelDestination.Home,
            TopLevelDestination.Discover,
            TopLevelDestination.Seasonal,
            TopLevelDestination.Setting,
        )
    }
    val isTopLevelDestination by remember {
        derivedStateOf {
            currentDestination?.isTopLevelDestination() == true
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ShanimeTheme.colors.background)
            .semantics { testTagsAsResourceId = true },
    ) {
        SharedTransitionLayout(
            modifier = Modifier.weight(weight = 1f),
        ) {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this,
            ) {
                val onNavigateUp: () -> Unit = remember {
                    {
                        navController.navigateUp()
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = HomeGraph,
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        ) + fadeIn(animationSpec = tween(easing = EaseIn))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(easing = EaseOut))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(easing = EaseIn))
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                        ) + fadeOut(animationSpec = tween(easing = EaseOut))
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    homeNavGraph(
                        onTopAnimeItemClick = navController::navigateToTopAnimeDetail,
                        onTopMangaItemClick = navController::navigateToTopMangaDetail,
                        onViewDetailClick = navController::navigateToBannerDetail,
                        onViewAllTopAnimeClick = navController::navigateToTopHitAnime,
                        onViewAllTopMangaClick = navController::navigateToTopHitManga,
                        onSearchAnimeClick = navController::navigateToSearchAnime,
                        onSearchMangaClick = navController::navigateToSearchManga,
                        onNavigateUp = onNavigateUp,
                    )
                    discoverNavGraph(
                        onSearchClick = navController::navigateToSearchAnime,
                        onDiscoverItemClick = navController::navigateToAnimeDetail,
                        onNavigateUp = onNavigateUp,
                    )
                    seasonalNavGraph(
                        onSeasonalItemClick = navController::navigateToSeasonalAnimeDetail,
                        onArchivedItemClick = navController::navigateToSeasonalAnimeDetail,
                        onArchivedSelected = navController::navigateToArchive,
                        onNavigateUp = onNavigateUp,
                    )
                    settingNavGraph(
                        onFontSizeClick = navController::navigateToFontSize,
                        onTermAndConditionClick = navController::navigateToTermAndCondition,
                        onAboutUsClick = navController::navigateToAboutUs,
                        onNavigateUp = onNavigateUp,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isTopLevelDestination,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }) + shrinkVertically(),
        ) {
            ShanimeBottomNavigation(
                destinations = bottomBarDestinations,
                onNavigateToDestination = { destination ->
                    navigationToTopLevelDestination(
                        navController = navController,
                        topLevelDestination = destination,
                    )
                },
                currentDestination = currentDestination,
            )
        }
    }
}
