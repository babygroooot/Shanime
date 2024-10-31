package com.babygroot.shanime.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import com.babygroot.shanime.TopLevelDestination
import com.core.designsystem.ShanimeTheme
import com.feature.discover.navigation.DiscoverDestinations
import com.feature.home.navigation.HomeDestinations
import com.feature.seasonal.navigation.SeasonalDestinations
import com.feature.setting.navigation.SettingDestinations

@Composable
fun ShanimeBottomNavigation(
    destinations: List<TopLevelDestination<out Any>>,
    onNavigateToDestination: (TopLevelDestination<out Any>) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = ShanimeTheme.colors.background,
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.route::class)
            } == true
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF06C149) else Color(0xFF878787),
                animationSpec = tween(durationMillis = 400),
                label = "",
            )
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    AnimatedContent(
                        targetState = isSelected,
                        transitionSpec = {
                            fadeIn(tween(durationMillis = 400)).togetherWith(
                                fadeOut(tween(durationMillis = 400)),
                            )
                        },
                        label = "",
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = destination.selectedIcon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = destination.unselectedIcon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = destination.name),
                        color = textColor,
                        style = ShanimeTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                ),
                modifier = Modifier.testTag(tag = destination.testTag),
            )
        }
    }
}

fun navigationToTopLevelDestination(
    navController: NavController,
    topLevelDestination: TopLevelDestination<out Any>,
) {
    val navOption = navOptions {
        popUpTo(id = navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    navController.navigate(route = topLevelDestination.route, navOptions = navOption)
}

fun NavDestination.isTopLevelDestination() = this.hasRoute(HomeDestinations.Home::class).or(
    this.hasRoute(DiscoverDestinations.Discover::class).or(
        this.hasRoute(SeasonalDestinations.Seasonal::class).or(
            this.hasRoute(SettingDestinations.Setting::class),
        ),
    ),
)
