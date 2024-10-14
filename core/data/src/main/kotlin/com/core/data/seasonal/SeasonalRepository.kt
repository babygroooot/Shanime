package com.core.data.seasonal

import com.core.data.seasonal.pagingsource.AiringAnimePagingSource
import com.core.data.seasonal.pagingsource.UpcomingAnimePagingSource

interface SeasonalRepository {

    fun getAiringAnimePagingSource(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
    ): AiringAnimePagingSource

    fun getUpcomingAnimePagingSource(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
    ): UpcomingAnimePagingSource
}
