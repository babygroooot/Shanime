package com.feature.discover.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.core.domain.discover.SearchAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    searchAnimeUseCase: SearchAnimeUseCase,
) : ViewModel() {

    var searchValue by mutableStateOf("")
        private set

    @OptIn(FlowPreview::class)
    val searchAnime = snapshotFlow { searchValue }.debounce(timeoutMillis = 500L)
        .distinctUntilChanged()
        .flatMapLatest { search ->
            if (search.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                searchAnimeUseCase(searchValue = search)
            }
        }.cachedIn(scope = viewModelScope)

    fun onSearchValueChange(value: String) {
        searchValue = value
    }
}
