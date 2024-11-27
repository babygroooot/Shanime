package com.feature.setting.navigation

import androidx.navigation.NavController

fun NavController.navigateToFontSize() {
    navigate(
        route = SettingDestinations.FontSize,
    )
}

fun NavController.navigateToTermAndCondition() {
    navigate(
        route = SettingDestinations.TermAndCondition,
    )
}

fun NavController.navigateToAboutUs() {
    navigate(
        route = SettingDestinations.AboutUs,
    )
}
