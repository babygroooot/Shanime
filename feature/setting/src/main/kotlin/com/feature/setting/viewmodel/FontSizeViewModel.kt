package com.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.main.GetUserSettingUseCase
import com.core.domain.main.SetFontSizeConfigUseCase
import com.core.model.main.ShanimeFontSizeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FontSizeViewModel @Inject constructor(
    getUserSettingUseCase: GetUserSettingUseCase,
    private val setFontSizeConfigUseCase: SetFontSizeConfigUseCase,
) : ViewModel() {

    val fontSizeConfig = getUserSettingUseCase().map { userSetting ->
        when (userSetting.fontSizeConfig) {
            ShanimeFontSizeConfig.NORMAL -> 0f
            ShanimeFontSizeConfig.MEDIUM -> 50f
            ShanimeFontSizeConfig.LARGE -> 100f
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = 0f,
    )

    fun onFontSizeChange(value: Float) {
        viewModelScope.launch {
            when (value) {
                0f -> setFontSizeConfigUseCase(fontSizeConfig = ShanimeFontSizeConfig.NORMAL)
                50f -> setFontSizeConfigUseCase(fontSizeConfig = ShanimeFontSizeConfig.MEDIUM)
                else -> setFontSizeConfigUseCase(fontSizeConfig = ShanimeFontSizeConfig.LARGE)
            }
        }
    }
}
