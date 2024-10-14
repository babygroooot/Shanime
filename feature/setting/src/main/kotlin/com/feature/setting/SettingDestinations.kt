package com.feature.setting

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingDestinations {
    @Serializable
    data object Setting : SettingDestinations

    @Serializable
    data object FontSize : SettingDestinations
}

@Serializable
data object SettingGraph
