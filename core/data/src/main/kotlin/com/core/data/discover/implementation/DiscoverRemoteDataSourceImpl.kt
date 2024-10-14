package com.core.data.discover.implementation

import com.core.common.network.Dispatcher
import com.core.common.network.ShanimeDispatcher
import com.core.data.ErrorDTO
import com.core.data.discover.DiscoverRemoteDataSource
import com.core.data.discover.DiscoverService
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.network.util.NetworkResult
import com.core.network.util.RetrofitUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DiscoverRemoteDataSourceImpl @Inject constructor(
    retrofitUtil: RetrofitUtil,
    @Dispatcher(ShanimeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : DiscoverRemoteDataSource {

    private val service = retrofitUtil.createApiService(DiscoverService::class.java)

    override suspend fun getTopAnime(
        type: String,
        filter: String,
        rating: String,
        sfw: Boolean,
        page: Int,
        limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getTopAnime(
            type = type,
            filter = filter,
            rating = rating,
            sfw = sfw,
            page = page,
            limit = limit,
        )
    }

    override suspend fun searchAnime(
        searchValue: String,
        page: Int,
        limit: Int,
    ): NetworkResult<TopAnimeResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.searchAnime(
            searchValue = searchValue,
            page = page,
            limit = limit,
            unapproved = false,
        )
    }
}
