package com.core.data.discover.implementation

import com.core.data.discover.DiscoverRemoteDataSource
import com.core.data.discover.DiscoverRepository
import com.core.data.discover.SearchAnimePagingSource
import com.core.data.discover.TopAnimePagingSource
import javax.inject.Inject

internal class DiscoverRepositoryImpl @Inject constructor(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource,
) : DiscoverRepository {
    override fun getTopAnimePagingSource(
        type: String,
        filter: String,
        rating: String,
        sfw: Boolean,
    ): TopAnimePagingSource = TopAnimePagingSource(
        getTopAnime = discoverRemoteDataSource::getTopAnime,
        type = type,
        filter = filter,
        rating = rating,
        sfw = sfw,
    )

    override fun searchAnimePagingSource(
        searchValue: String,
    ): SearchAnimePagingSource = SearchAnimePagingSource(
        searchAnime = discoverRemoteDataSource::searchAnime,
        searchValue = searchValue,
    )
}
