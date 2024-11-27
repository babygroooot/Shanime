package com.feature.setting.navigation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feature.setting.ui.AboutUsScreen
import com.feature.setting.ui.FontSizeScreen
import com.feature.setting.ui.SettingScreen
import com.feature.setting.ui.TermAndConditionScreen

fun NavGraphBuilder.settingNavGraph(
    onFontSizeClick: () -> Unit,
    onTermAndConditionClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    navigation<SettingGraph>(startDestination = SettingDestinations.Setting) {
        composable<SettingDestinations.Setting>(
            enterTransition = { fadeIn(tween(easing = EaseIn)) },
            exitTransition = { fadeOut(tween(easing = EaseOut)) },
            popEnterTransition = { fadeIn(tween(easing = EaseIn)) },
            popExitTransition = { fadeOut(tween(easing = EaseOut)) },
        ) {
            SettingScreen(
                onFontSizeClick = onFontSizeClick,
                onTermAndConditionClick = onTermAndConditionClick,
                onAboutUsClick = onAboutUsClick,
            )
        }
        composable<SettingDestinations.FontSize> {
            FontSizeScreen(
                onNavigateUp = onNavigateUp,
            )
        }
        composable<SettingDestinations.TermAndCondition> {
            TermAndConditionScreen(
                onNavigateUp = onNavigateUp,
            )
        }
        composable<SettingDestinations.AboutUs> {
            AboutUsScreen(
                onNavigateUp = onNavigateUp,
            )
        }
    }
}
