package com.core.data.main.implementation

import com.core.data.main.MainLocalDataSource
import com.core.data.main.UserSettingData
import com.core.datastore.usersetting.UserSettingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MainLocalDataSourceImpl @Inject constructor(
    private val userSettingDataSource: UserSettingDataSource,
) : MainLocalDataSource {

    override val userSetting: Flow<UserSettingData>
        get() = userSettingDataSource.userSetting.map { userSetting ->
            UserSettingData(
                theme = userSetting.theme,
                fontSize = userSetting.fontSize,
                language = userSetting.language,
            )
        }

    override suspend fun setThemeConfig(config: String) {
        userSettingDataSource.setThemeConfig(config = config)
    }

    override suspend fun setFontSizeConfig(config: String) {
        userSettingDataSource.setFontSizeConfig(config = config)
    }

    override suspend fun setPreferredLanguage(config: String) {
        userSettingDataSource.setPreferredLanguage(config = config)
    }
}
