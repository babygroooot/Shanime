package com.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.main.GetUserSettingUseCase
import com.core.domain.main.SetThemeConfigUseCase
import com.core.model.main.ShanimeThemeConfig
import com.core.model.main.UserSettingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getUserSettingUseCase: GetUserSettingUseCase,
    private val setThemeConfigUseCase: SetThemeConfigUseCase,
) : ViewModel() {

    val userSetting = getUserSettingUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = UserSettingModel.getDefaultInstance(),
    )

    fun setThemeConfig(config: ShanimeThemeConfig) {
        viewModelScope.launch {
            setThemeConfigUseCase(themeConfig = config)
        }
    }
}
