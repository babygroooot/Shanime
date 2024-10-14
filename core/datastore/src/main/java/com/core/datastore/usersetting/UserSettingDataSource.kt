package com.core.datastore.usersetting

import androidx.datastore.core.DataStore
import com.core.datastore.UserSettingOuterClass.UserSetting
import com.core.datastore.copy
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingDataSource @Inject constructor(
    private val userSettingPref: DataStore<UserSetting>,
) {

    val userSetting = userSettingPref.data.map {
        UserSettingPref(
            theme = it.theme,
            fontSize = it.fontSize,
            language = it.language,
        )
    }

    suspend fun setThemeConfig(config: String) {
        userSettingPref.updateData { userSetting ->
            userSetting.copy {
                theme = config
            }
        }
    }

    suspend fun setPreferredLanguage(config: String) {
        userSettingPref.updateData { userSetting ->
            userSetting.copy {
                language = config
            }
        }
    }

    suspend fun setFontSizeConfig(config: String) {
        userSettingPref.updateData { userSetting ->
            userSetting.copy {
                fontSize = config
            }
        }
    }
}
