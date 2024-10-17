package com.babygroot.shanime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.main.GetUserSettingUseCase
import com.core.domain.main.SetPreferredLanguageUseCase
import com.core.model.main.ShanimeLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserSettingUseCase: GetUserSettingUseCase,
    private val setPreferredLanguageUseCase: SetPreferredLanguageUseCase,
) : ViewModel() {

    val userSetting = getUserSettingUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = null,
    )

    fun setPreferredLanguage(language: ShanimeLanguage) {
        viewModelScope.launch {
            setPreferredLanguageUseCase(preferredLanguage = language)
        }
    }
}
