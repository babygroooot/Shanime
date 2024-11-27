package com.core.data.seasonal.implementation

import com.core.common.network.Dispatcher
import com.core.common.network.ShanimeDispatcher
import com.core.data.ErrorDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.seasonal.SeasonalRemoteDataSource
import com.core.data.seasonal.SeasonalService
import com.core.data.seasonal.dto.ArchivedAnimeResponseDTO
import com.core.data.seasonal.dto.SeasonResponseDTO
import com.core.network.util.NetworkResult
import com.core.network.util.RetrofitUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SeasonalRemoteDataSourceImpl @Inject constructor(
    retrofitUtil: RetrofitUtil,
    @Dispatcher(ShanimeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : SeasonalRemoteDataSource {

    private val service = retrofitUtil.createApiService(SeasonalService::class.java)

    override suspend fun getAiringAnime(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getAiringSeasonalAnime(
            filter = filter,
            sfw = sfw,
            unapproved = unapproved,
            continuing = continuing,
            page = page,
            limit = limit,
        )
    }

    override suspend fun getUpcomingAnime(
        filter: String,
        sfw: Boolean,
        unapproved: Boolean,
        continuing: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getUpcomingAnime(
            filter = filter,
            sfw = sfw,
            unapproved = unapproved,
            continuing = continuing,
            page = page,
            limit = limit,
        )
    }

    override suspend fun getSeasons(): NetworkResult<SeasonResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getSeasons()
    }

    override suspend fun getArchivedAnime(
        year: String,
        season: String,
        sfw: Boolean,
        unapproved: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<ArchivedAnimeResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getArchivedAnime(
            year = year,
            season = season,
            sfw = sfw,
            unapproved = unapproved,
            page = page,
            limit = limit,
        )
    }
}
