package com.core.data.home.implementation

import com.core.common.network.Dispatcher
import com.core.common.network.ShanimeDispatcher
import com.core.data.ErrorDTO
import com.core.data.home.HomeRemoteDataSource
import com.core.data.home.HomeService
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.data.home.dto.UserCommentResponseDTO
import com.core.network.util.NetworkResult
import com.core.network.util.RetrofitUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class HomeRemoteDataSourceImpl @Inject constructor(
    retrofitUtil: RetrofitUtil,
    @Dispatcher(ShanimeDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : HomeRemoteDataSource {

    private val service = retrofitUtil.createApiService(HomeService::class.java)

    override suspend fun getAiringSeasonalAnime(
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

    override suspend fun getTopManga(
        type: String,
        filter: String,
        page: Int,
        limit: Int,
    ): NetworkResult<TopMangaResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getTopManga(
            type = type,
            filter = filter,
            page = page,
            limit = limit,
        )
    }

    override suspend fun getAnimeUserComments(
        id: Long,
        page: Int,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getAnimeUserComments(
            id = id,
            page = page,
            isPreliminary = isPreliminary,
            isSpoiler = isSpoiler,
        )
    }

    override suspend fun getMangaUserComments(
        id: Long,
        page: Int,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ): NetworkResult<UserCommentResponseDTO, ErrorDTO> = withContext(ioDispatcher) {
        service.getMangaUserComments(
            id = id,
            page = page,
            isPreliminary = isPreliminary,
            isSpoiler = isSpoiler,
        )
    }
}
