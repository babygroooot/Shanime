package com.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.core.domain.discover.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAnimeViewModel @Inject constructor(
    getTopAnimeUseCase: GetTopAnimeUseCase,
) : ViewModel() {

    val topAnime = getTopAnimeUseCase(
        type = "tv",
        filter = "",
        rating = "",
        sfw = true,
    ).cachedIn(scope = viewModelScope)
}
