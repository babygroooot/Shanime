package com.feature.setting.navigation

import androidx.navigation.NavController

fun NavController.navigateToFontSize() {
    navigate(
        route = SettingDestinations.FontSize,
    )
}
