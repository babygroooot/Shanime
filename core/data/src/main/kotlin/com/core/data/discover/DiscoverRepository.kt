package com.core.data.discover

interface DiscoverRepository {

    fun getTopAnimePagingSource(
        type: String,
        filter: String,
        rating: String,
        sfw: Boolean,
    ): TopAnimePagingSource

    fun searchAnimePagingSource(
        searchValue: String,
    ): SearchAnimePagingSource
}
