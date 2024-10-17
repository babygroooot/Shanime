package com.feature.setting.ui

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PlaylistAddCheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.ThemeSwitcher
import com.core.model.main.ShanimeLanguage
import com.core.model.main.ShanimeThemeConfig
import com.feature.setting.R
import com.feature.setting.SettingDestinations
import com.feature.setting.viewmodel.SettingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    isDarkTheme: Boolean,
    selectedLanguage: ShanimeLanguage,
    onSwitchTheme: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_setting_setting),
                        style = ShanimeTheme.typography.navigationTitle,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                },
                actions = {
                    ThemeSwitcher(
                        isDark = isDarkTheme,
                        color = ShanimeTheme.colors.textPrimary,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = onSwitchTheme,
                            )
                            .testTag(tag = "theme_switcher"),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        var showLanguageBottomSheet by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(
                        color = ShanimeTheme.colors.secondary,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(20.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    val brush = Brush.sweepGradient(
                        listOf(
                            Color.Red,
                            Color.Green,
                            Color.Blue,
                            Color.Red,
                        ),
                    )
                    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
                    val angle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 4_000, easing = EaseIn),
                            repeatMode = RepeatMode.Restart,
                        ),
                        label = "angleAnimation",
                    )
                    Text(
                        text = "Shanime",
                        style = ShanimeTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(id = R.string.feature_setting_app_desc),
                        style = ShanimeTheme.typography.bodyMedium,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    ElevatedCard(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = ShanimeTheme.colors.background,
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(9.dp))
                            .padding(1.dp)
                            .drawWithCache {
                                onDrawWithContent {
                                    rotate(degrees = angle) {
                                        drawCircle(
                                            brush = brush,
                                            radius = size.width,
                                            blendMode = BlendMode.SrcIn,
                                        )
                                    }
                                    drawContent()
                                }
                            },
                    ) {
                        Text(
                            text = stringResource(id = R.string.feature_setting_version) + " 1.0.0",
                            style = ShanimeTheme.typography.bodyMedium,
                            color = ShanimeTheme.colors.textPrimary,
                            modifier = Modifier.padding(10.dp),
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_shanime),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                )
            }
            SettingMenuItem(
                icon = Icons.Rounded.Language,
                title = stringResource(id = R.string.feature_setting_language),
                onClick = {
                    showLanguageBottomSheet = true
                },
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            SettingMenuItem(
                icon = Icons.Rounded.FormatSize,
                title = stringResource(id = R.string.feature_setting_font_size),
                onClick = {
                    navController.navigate(SettingDestinations.FontSize)
                },
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            SettingMenuItem(
                icon = Icons.Rounded.PlaylistAddCheckCircle,
                title = stringResource(id = R.string.feature_setting_term_and_condition),
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            SettingMenuItem(
                icon = Icons.Outlined.Info,
                title = stringResource(id = R.string.feature_setting_about_us),
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            if (showLanguageBottomSheet) {
                LanguageBottomSheet(
                    sheetState = sheetState,
                    selectedLanguage = selectedLanguage,
                    onLanguageSelected = { selectedLanguage ->
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(selectedLanguage.languageTag),
                        )
                    },
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (sheetState.isVisible.not()) {
                                showLanguageBottomSheet = false
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun SettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val userSetting by viewModel.userSetting.collectAsStateWithLifecycle()
    val currentTheme = getCurrentTheme(config = userSetting.themeConfig)
    val context = LocalContext.current
    SettingScreen(
        isDarkTheme = currentTheme == ShanimeThemeConfig.DARK,
        selectedLanguage = userSetting.preferredLanguage,
        onSwitchTheme = {
            val config = if (currentTheme == ShanimeThemeConfig.DARK) {
                ShanimeThemeConfig.LIGHT
            } else {
                ShanimeThemeConfig.DARK
            }
            context.setUiMode(config = config)
            viewModel.setThemeConfig(
                config = config,
            )
        },
        navController = navController,
        modifier = modifier,
    )
}

@Composable
private fun SettingMenuItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = ShanimeTheme.colors.surface,
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ShanimeTheme.colors.textPrimary,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = ShanimeTheme.colors.background,
                        shape = CircleShape,
                    )
                    .padding(12.dp),
            )
            Text(
                text = title,
                style = ShanimeTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ShanimeTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = ShanimeTheme.colors.textPrimary,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingScreenPreview() {
    ShanimeTheme {
        SettingScreen(
            isDarkTheme = false,
            selectedLanguage = ShanimeLanguage.ENGLISH,
            onSwitchTheme = {},
            navController = rememberNavController(),
        )
    }
}

@PreviewLightDark
@Composable
private fun SettingMenuItemPreview() {
    ShanimeTheme {
        SettingMenuItem(
            icon = Icons.Rounded.Edit,
            title = "Some random title",
        )
    }
}

@Composable
private fun getCurrentTheme(
    config: ShanimeThemeConfig,
): ShanimeThemeConfig {
    val context = LocalContext.current
    val uiModeManager = context.getSystemService(UiModeManager::class.java)
    return when (config) {
        ShanimeThemeConfig.FOLLOW_SYSTEM -> {
            val systemTheme = uiModeManager.nightMode
            if (systemTheme == UiModeManager.MODE_NIGHT_YES) {
                ShanimeThemeConfig.DARK
            } else {
                ShanimeThemeConfig.LIGHT
            }
        }

        ShanimeThemeConfig.LIGHT -> ShanimeThemeConfig.LIGHT

        ShanimeThemeConfig.DARK -> ShanimeThemeConfig.DARK

        else -> ShanimeThemeConfig.LIGHT
    }
}

private fun Context.setUiMode(config: ShanimeThemeConfig) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
    val uiModeManager = getSystemService(UiModeManager::class.java)
    val mode = when (config) {
        ShanimeThemeConfig.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO

        ShanimeThemeConfig.DARK -> AppCompatDelegate.MODE_NIGHT_YES

        ShanimeThemeConfig.FOLLOW_SYSTEM -> {
            val systemTheme = uiModeManager.nightMode
            if (systemTheme == UiModeManager.MODE_NIGHT_YES) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        }
    }
    uiModeManager.setApplicationNightMode(mode)
}
