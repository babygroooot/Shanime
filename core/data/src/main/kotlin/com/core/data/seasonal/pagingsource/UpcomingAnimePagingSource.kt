package com.core.data.seasonal.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.data.ErrorDTO
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.AiringSeasonalAnimeDTO
import com.core.data.home.dto.AiringSeasonalAnimeResponseDTO
import com.core.network.util.NetworkResult

class UpcomingAnimePagingSource(
    private val getUpcomingAnime: suspend (filter: String, sfw: Boolean, unapproved: Boolean, continuing: Boolean, page: Int, limit: Int) -> NetworkResult<AiringSeasonalAnimeResponseDTO, ErrorDTO>,
    private val filter: String,
    private val sfw: Boolean,
    private val unapproved: Boolean,
    private val continuing: Boolean,
) : PagingSource<Int, AiringSeasonalAnimeDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AiringSeasonalAnimeDTO> = try {
        val currentPage = params.key ?: 1
        val response = getUpcomingAnime(filter, sfw, unapproved, continuing, currentPage, ITEM_LIMIT)
        if (response is NetworkResult.Success) {
            LoadResult.Page(
                data = response.data.data.distinctBy { it.malId },
                prevKey = if (currentPage == 1) null else currentPage.dec(),
                nextKey = if (response.data.pagination.hasNextPage) currentPage.inc() else null,
            )
        } else {
            LoadResult.Error(Exception())
        }
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }

    override fun getRefreshKey(state: PagingState<Int, AiringSeasonalAnimeDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
