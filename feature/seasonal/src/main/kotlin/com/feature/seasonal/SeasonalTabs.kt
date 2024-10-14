package com.feature.seasonal

import androidx.annotation.StringRes

enum class SeasonalTabs(
    @StringRes val title: Int,
) {
    THIS_SEASON(title = R.string.feature_seasonal_this_season),
    UPCOMING(title = R.string.feature_seasonal_upcoming),
}
