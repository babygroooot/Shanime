package com.core.data.home.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.common.network.exception.NetworkErrorException
import com.core.data.ErrorDTO
import com.core.data.ITEM_LIMIT
import com.core.data.home.dto.TopMangaDTO
import com.core.data.home.dto.TopMangaResponseDTO
import com.core.network.util.NetworkResult

class SearchMangaPagingSource(
    private val searchManga: suspend (searchValue: String, page: Int, limit: Int, unapproved: Boolean) -> NetworkResult<TopMangaResponseDTO, ErrorDTO>,
    private val search: String,
) : PagingSource<Int, TopMangaDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopMangaDTO> = try {
        val currentPage = params.key ?: 1
        val response = searchManga(search, currentPage, ITEM_LIMIT, false)
        if (response is NetworkResult.Success) {
            LoadResult.Page(
                data = response.data.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.data.pagination.hasNextPage) currentPage.inc() else null,
            )
        } else {
            LoadResult.Error(throwable = NetworkErrorException())
        }
    } catch (exception: Exception) {
        LoadResult.Error(throwable = exception)
    }

    override fun getRefreshKey(state: PagingState<Int, TopMangaDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
