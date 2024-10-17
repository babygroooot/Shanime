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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.feature.discover.discoverNavGraph
import com.feature.home.HomeGraph
import com.feature.home.homeNavGraph
import com.feature.seasonal.seasonalNavGraph
import com.feature.setting.settingNavGraph

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
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            /*if (isTopLevelDestination) {
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
            }*/
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
        },
        modifier = modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true },
    ) { innerPadding ->
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this,
            ) {
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
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    homeNavGraph(navController = navController)
                    discoverNavGraph(navController = navController)
                    seasonalNavGraph(navController = navController)
                    settingNavGraph(navController = navController)
                }
            }
        }
    }
}
