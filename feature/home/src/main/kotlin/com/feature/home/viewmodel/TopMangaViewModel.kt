package com.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.core.domain.home.GetTopMangaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopMangaViewModel @Inject constructor(
    getTopMangaUseCase: GetTopMangaUseCase,
) : ViewModel() {

    val topManga = getTopMangaUseCase().cachedIn(
        scope = viewModelScope,
    )
}
