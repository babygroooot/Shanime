package com.core.data.seasonal.implementation

import com.core.data.seasonal.SeasonalRemoteDataSource
import com.core.data.seasonal.SeasonalRepository
import com.core.data.seasonal.pagingsource.AiringAnimePagingSource
import com.core.data.seasonal.pagingsource.UpcomingAnimePagingSource
import javax.inject.Inject

internal class SeasonalRepositoryImpl @Inject constructor(
    private val seasonalRemoteDataSource: SeasonalRemoteDataSource,
) : SeasonalRepository {
    override fun getAiringAnimePagingSource(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
    ): AiringAnimePagingSource = AiringAnimePagingSource(
        getAiringAnime = seasonalRemoteDataSource::getAiringAnime,
        filter = filter,
        sfw = sfw,
        unapproved = unapproved,
        continuing = continuing,
    )

    override fun getUpcomingAnimePagingSource(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
    ): UpcomingAnimePagingSource = UpcomingAnimePagingSource(
        getUpcomingAnime = seasonalRemoteDataSource::getUpcomingAnime,
        filter = filter,
        sfw = sfw,
        unapproved = unapproved,
        continuing = continuing,
    )
}
