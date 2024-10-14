package com.core.data.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.data.ErrorDTO
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.TopAnimeDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.network.util.NetworkResult

class TopAnimePagingSource(
    private val getTopAnime: suspend (type: String, filter: String, rating: String, sfw: Boolean, page: Int, limit: Int) -> NetworkResult<TopAnimeResponseDTO, ErrorDTO>,
    private val type: String,
    private val filter: String,
    private val rating: String,
    private val sfw: Boolean,
) : PagingSource<Int, TopAnimeDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopAnimeDTO> = try {
        val currentPage = params.key ?: 1
        val response = getTopAnime(type, filter, rating, sfw, currentPage, ITEM_LIMIT)
        if (response is NetworkResult.Success) {
            val itemBefore = currentPage.dec() * ITEM_LIMIT
            val itemAfter = response.data.pagination.items.total - (itemBefore + response.data.pagination.items.count)
            LoadResult.Page(
                data = response.data.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.data.pagination.hasNextPage) currentPage.inc() else null,
                itemsBefore = itemBefore,
                itemsAfter = itemAfter,
            )
        } else {
            LoadResult.Error(
                throwable = Exception(),
            )
        }
    } catch (exception: Exception) {
        LoadResult.Error(
            throwable = exception,
        )
    }

    override val jumpingSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, TopAnimeDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
