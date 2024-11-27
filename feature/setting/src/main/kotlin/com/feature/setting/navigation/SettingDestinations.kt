package com.feature.setting.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingDestinations {
    @Serializable
    data object Setting : SettingDestinations

    @Serializable
    data object FontSize : SettingDestinations

    @Serializable
    data object TermAndCondition : SettingDestinations

    @Serializable
    data object AboutUs : SettingDestinations
}

@Serializable
data object SettingGraph
