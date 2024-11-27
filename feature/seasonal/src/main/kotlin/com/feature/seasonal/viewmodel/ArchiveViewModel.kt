package com.feature.seasonal.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.core.domain.seasonal.GetArchivedAnimeUseCase
import com.feature.seasonal.navigation.SeasonalDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getArchivedAnimeUseCase: GetArchivedAnimeUseCase,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<SeasonalDestinations.Archive>()

    val archivedAnime = getArchivedAnimeUseCase(
        year = args.year,
        season = args.season,
    ).cachedIn(scope = viewModelScope)
}
