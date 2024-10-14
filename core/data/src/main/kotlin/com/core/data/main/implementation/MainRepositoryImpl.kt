package com.core.data.main.implementation

import com.core.data.main.MainLocalDataSource
import com.core.data.main.MainRepository
import com.core.data.main.UserSettingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MainRepositoryImpl @Inject constructor(
    private val mainLocalDataSource: MainLocalDataSource,
) : MainRepository {

    override val userSetting: Flow<UserSettingData>
        get() = mainLocalDataSource.userSetting

    override suspend fun setThemeConfig(config: String) {
        mainLocalDataSource.setThemeConfig(config = config)
    }

    override suspend fun setFontSizeConfig(config: String) {
        mainLocalDataSource.setFontSizeConfig(config = config)
    }

    override suspend fun setPreferredLanguage(config: String) {
        mainLocalDataSource.setPreferredLanguage(config = config)
    }
}
