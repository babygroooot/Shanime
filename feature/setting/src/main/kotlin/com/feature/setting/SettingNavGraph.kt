package com.feature.setting

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feature.setting.ui.FontSizeScreen
import com.feature.setting.ui.SettingScreen

fun NavGraphBuilder.settingNavGraph(
    navController: NavController,
) {
    navigation<SettingGraph>(startDestination = SettingDestinations.Setting) {
        composable<SettingDestinations.Setting>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            SettingScreen(
                navController = navController,
            )
        }
        composable<SettingDestinations.FontSize> {
            FontSizeScreen(
                navController = navController,
            )
        }
    }
}
