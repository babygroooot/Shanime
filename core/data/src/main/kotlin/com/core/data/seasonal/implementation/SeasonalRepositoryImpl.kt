package com.core.data.seasonal.implementation

import com.core.common.network.Dispatcher
import com.core.common.network.ShanimeDispatcher
import com.core.common.network.exception.NetworkErrorException
import com.core.data.seasonal.SeasonalRemoteDataSource
import com.core.data.seasonal.SeasonalRepository
import com.core.data.seasonal.dto.SeasonResponseDTO
import com.core.data.seasonal.pagingsource.AiringAnimePagingSource
import com.core.data.seasonal.pagingsource.ArchivedAnimePagingSource
import com.core.data.seasonal.pagingsource.UpcomingAnimePagingSource
import com.core.network.util.onError
import com.core.network.util.onException
import com.core.network.util.onSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class SeasonalRepositoryImpl @Inject constructor(
    private val seasonalRemoteDataSource: SeasonalRemoteDataSource,
    @Dispatcher(ShanimeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
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

    override fun getSeasons(): Flow<Result<SeasonResponseDTO>> = flow<Result<SeasonResponseDTO>> {
        val response = seasonalRemoteDataSource.getSeasons()
        response.onSuccess { seasonalResponseDTO ->
            emit(Result.success(seasonalResponseDTO))
        }
        response.onException { exception, _ ->
            emit(Result.failure(exception = exception))
        }
        response.onError { _, _, errorData ->
            emit(Result.failure(NetworkErrorException(errorData?.message)))
        }
    }.flowOn(ioDispatcher)

    override fun getArchivedAnimePagingSource(
        year: String,
        season: String,
    ): ArchivedAnimePagingSource = ArchivedAnimePagingSource(
        getArchivedAnime = seasonalRemoteDataSource::getArchivedAnime,
        year = year,
        season = season,
    )
}
