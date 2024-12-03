package com.babygroot.shanime.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.babygroot.shanime.viewmodel.MainViewModel
import com.core.designsystem.FontSizeConfig
import com.core.designsystem.PreferredLanguage
import com.core.designsystem.ShanimeTheme
import com.core.model.main.ShanimeFontSizeConfig
import com.core.model.main.ShanimeLanguage
import com.core.model.main.ShanimeThemeConfig
import com.core.model.main.UserSettingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val languageHasNotBeenSet = AppCompatDelegate.getApplicationLocales().toLanguageTags().isEmpty()
        if (languageHasNotBeenSet) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(ShanimeLanguage.ENGLISH.languageTag))
        }
        var userSetting by mutableStateOf<UserSettingModel?>(null)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userSetting.collect { userSettingModel ->
                    userSetting = userSettingModel
                }
            }
        }
        // A guard condition to avoid a bug where the app stuck on
        // splash screen for the initial launch out of fresh install
        if (!languageHasNotBeenSet) {
            splashScreen.setKeepOnScreenCondition {
                userSetting == null
            }
        }
        setContent {
            val fontSizeConfig by remember {
                derivedStateOf {
                    when (userSetting?.fontSizeConfig) {
                        ShanimeFontSizeConfig.NORMAL -> FontSizeConfig.NORMAL
                        ShanimeFontSizeConfig.MEDIUM -> FontSizeConfig.MEDIUM
                        ShanimeFontSizeConfig.LARGE -> FontSizeConfig.LARGE
                        else -> FontSizeConfig.NORMAL
                    }
                }
            }
            val preferredLanguage by remember {
                derivedStateOf {
                    when (userSetting?.preferredLanguage) {
                        ShanimeLanguage.ENGLISH -> PreferredLanguage.ENGLISH
                        ShanimeLanguage.KHMER -> PreferredLanguage.KHMER
                        else -> PreferredLanguage.ENGLISH
                    }
                }
            }
            val shouldUseDarkTheme = shouldUseDarkTheme(config = userSetting?.themeConfig ?: ShanimeThemeConfig.FOLLOW_SYSTEM)
            DisposableEffect(userSetting?.themeConfig) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { shouldUseDarkTheme },
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = lightScrim,
                        darkScrim = darkScrim,
                        detectDarkMode = { shouldUseDarkTheme },
                    ),
                )
                onDispose {}
            }
            ShanimeTheme(
                darkTheme = shouldUseDarkTheme,
                fontSizeConfig = fontSizeConfig,
                preferredLanguage = preferredLanguage,
            ) {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController,
                )
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val previouslyPreferredLanguage = viewModel.userSetting.first()?.preferredLanguage?.languageTag
                val currentlyPreferredLanguage = resources.configuration.locales[0].toLanguageTag()
                if (previouslyPreferredLanguage != currentlyPreferredLanguage) {
                    /*
                      Syncs user preferred language to our custom local storage
                      when user selects either in in-app language picker or per-app language setting
                     */
                    val preferredLanguage = ShanimeLanguage.entries.find { language ->
                        language.languageTag == currentlyPreferredLanguage
                    } ?: ShanimeLanguage.ENGLISH
                    viewModel.setPreferredLanguage(language = preferredLanguage)
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    config: ShanimeThemeConfig,
): Boolean = when (config) {
    ShanimeThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    ShanimeThemeConfig.LIGHT -> false
    ShanimeThemeConfig.DARK -> true
    else -> false
}

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

private val darkScrim = Color.rgb(25, 26, 31)
