package com.feature.discover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.core.domain.discover.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    getTopAnimeUseCase: GetTopAnimeUseCase,
) : ViewModel() {
    val topAnime = getTopAnimeUseCase(
        type = "tv",
        filter = "favorite",
        rating = "pg13",
        sfw = true,
    ).cachedIn(scope = viewModelScope)
}
