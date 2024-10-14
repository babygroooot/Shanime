package com.core.data.main

import kotlinx.coroutines.flow.Flow

interface MainLocalDataSource {

    val userSetting: Flow<UserSettingData>

    suspend fun setThemeConfig(config: String)

    suspend fun setFontSizeConfig(config: String)

    suspend fun setPreferredLanguage(config: String)
}
