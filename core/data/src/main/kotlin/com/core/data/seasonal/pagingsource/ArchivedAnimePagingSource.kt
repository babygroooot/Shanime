package com.core.data.seasonal.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.data.ErrorDTO
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.TopAnimeDTO
import com.core.data.seasonal.dto.ArchivedAnimeResponseDTO
import com.core.network.util.NetworkResult

class ArchivedAnimePagingSource(
    private val getArchivedAnime: suspend (year: String, season: String, sfw: Boolean, unapproved: Boolean, page: Int, limit: Int) -> NetworkResult<ArchivedAnimeResponseDTO, ErrorDTO>,
    private val year: String,
    private val season: String,
) : PagingSource<Int, TopAnimeDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopAnimeDTO> =
        try {
            val currentPage = params.key ?: 1
            val response = getArchivedAnime(year, season, true, false, currentPage, ITEM_LIMIT)
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

    override fun getRefreshKey(state: PagingState<Int, TopAnimeDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
