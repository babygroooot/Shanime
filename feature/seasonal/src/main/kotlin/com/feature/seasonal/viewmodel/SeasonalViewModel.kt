package com.feature.seasonal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.core.domain.seasonal.GetAiringAnimeUseCase
import com.core.domain.seasonal.GetUpcomingAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonalViewModel @Inject constructor(
    getAiringAnimeUseCase: GetAiringAnimeUseCase,
    getUpcomingAnimeUseCase: GetUpcomingAnimeUseCase,
) : ViewModel() {

    val airingAnime = getAiringAnimeUseCase(
        filter = "",
        sfw = true,
        unapproved = false,
        continuing = true,
    ).cachedIn(scope = viewModelScope)

    val upcomingAnime = getUpcomingAnimeUseCase(
        filter = "",
        sfw = true,
        unapproved = false,
        continuing = true,
    ).cachedIn(scope = viewModelScope)
}
