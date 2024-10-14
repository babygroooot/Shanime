package com.core.data.discover

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.data.ErrorDTO
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.TopAnimeDTO
import com.core.data.home.dto.TopAnimeResponseDTO
import com.core.network.util.NetworkResult
import com.core.network.util.onException
import javax.inject.Inject

class SearchAnimePagingSource @Inject constructor(
    private val searchAnime: suspend (searchValue: String, page: Int, limit: Int) -> NetworkResult<TopAnimeResponseDTO, ErrorDTO>,
    private val searchValue: String,
) : PagingSource<Int, TopAnimeDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopAnimeDTO> = try {
        val currentPage = params.key ?: 1
        val response = searchAnime(searchValue, currentPage, ITEM_LIMIT)
        response.onException { e, code ->
            Log.d("dddddddddddddddddddd", "$e")
        }
        if (response is NetworkResult.Success) {
            LoadResult.Page(
                data = response.data.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.data.pagination.hasNextPage) currentPage.inc() else null,
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

    override fun getRefreshKey(state: PagingState<Int, TopAnimeDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
