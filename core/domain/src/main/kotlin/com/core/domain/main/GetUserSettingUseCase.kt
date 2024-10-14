package com.core.domain.main

import com.core.data.main.MainRepository
import com.core.model.main.ShanimeFontSizeConfig
import com.core.model.main.ShanimeLanguage
import com.core.model.main.ShanimeThemeConfig
import com.core.model.main.UserSettingModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserSettingUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) {

    operator fun invoke() = mainRepository.userSetting.map { userSetting ->
        UserSettingModel(
            themeConfig = ShanimeThemeConfig.entries.find { it.config == userSetting.theme } ?: ShanimeThemeConfig.FOLLOW_SYSTEM,
            fontSizeConfig = ShanimeFontSizeConfig.entries.find { it.config == userSetting.fontSize } ?: ShanimeFontSizeConfig.NORMAL,
            preferredLanguage = ShanimeLanguage.entries.find { it.languageTag == userSetting.language } ?: ShanimeLanguage.ENGLISH,
        )
    }
}
