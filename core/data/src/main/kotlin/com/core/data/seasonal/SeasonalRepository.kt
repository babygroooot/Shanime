package com.core.data.seasonal

import com.core.data.seasonal.dto.SeasonResponseDTO
import com.core.data.seasonal.pagingsource.AiringAnimePagingSource
import com.core.data.seasonal.pagingsource.ArchivedAnimePagingSource
import com.core.data.seasonal.pagingsource.UpcomingAnimePagingSource
import kotlinx.coroutines.flow.Flow

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

    fun getSeasons(): Flow<Result<SeasonResponseDTO>>

    fun getArchivedAnimePagingSource(
        year: String,
        season: String,
    ): ArchivedAnimePagingSource
}
