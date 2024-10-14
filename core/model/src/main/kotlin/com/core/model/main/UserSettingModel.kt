package com.core.model.main

data class UserSettingModel(
    val themeConfig: ShanimeThemeConfig,
    val fontSizeConfig: ShanimeFontSizeConfig,
    val preferredLanguage: ShanimeLanguage,
) {
    companion object {
        fun getDefaultInstance() = UserSettingModel(
            themeConfig = ShanimeThemeConfig.FOLLOW_SYSTEM,
            fontSizeConfig = ShanimeFontSizeConfig.NORMAL,
            preferredLanguage = ShanimeLanguage.ENGLISH,
        )
    }
}
