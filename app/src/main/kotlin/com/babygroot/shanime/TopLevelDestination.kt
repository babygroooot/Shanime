package com.babygroot.shanime

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.feature.discover.navigation.DiscoverGraph
import com.feature.home.navigation.HomeGraph
import com.feature.seasonal.navigation.SeasonalGraph
import com.feature.setting.navigation.SettingGraph
import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelDestination<T>(
    @StringRes val name: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: T,
    val testTag: String,
) {
    @Serializable
    data object Home : TopLevelDestination<HomeGraph>(
        name = com.feature.home.R.string.feature_home_home,
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected,
        route = HomeGraph,
        testTag = "HomeNav",
    )

    @Serializable
    data object Discover : TopLevelDestination<DiscoverGraph>(
        name = com.feature.discover.R.string.feature_discover_discover,
        selectedIcon = R.drawable.ic_discover_selected,
        unselectedIcon = R.drawable.ic_discover_unselected,
        route = DiscoverGraph,
        testTag = "DiscoverNav",
    )

    @Serializable
    data object Seasonal : TopLevelDestination<SeasonalGraph>(
        name = com.feature.seasonal.R.string.feature_seasonal_seasonal,
        selectedIcon = R.drawable.ic_seasonal_selected,
        unselectedIcon = R.drawable.ic_seasonal_unselected,
        route = SeasonalGraph,
        testTag = "SeasonalNav",
    )

    @Serializable
    data object Setting : TopLevelDestination<SettingGraph>(
        name = com.feature.setting.R.string.feature_setting_setting,
        selectedIcon = R.drawable.ic_setting_selected,
        unselectedIcon = R.drawable.ic_setting_unselected,
        route = SettingGraph,
        testTag = "SettingNav",
    )
}
