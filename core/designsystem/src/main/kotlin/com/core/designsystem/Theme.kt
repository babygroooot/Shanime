package com.core.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePainter.State.Error
import coil3.compose.AsyncImagePainter.State.Success
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.asPainter
import coil3.request.ErrorResult
import coil3.request.SuccessResult

val LocalShanimeColor = staticCompositionLocalOf {
    AppColorScheme(
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        shimmer = listOf(),
    )
}

val LocalShanimeTypography = staticCompositionLocalOf {
    ShanimeTypography(
        navigationTitle = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {
    error(message = "No SharedElementScope found")
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope> {
    error(message = "No AnimatedVisibilityScope found")
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalCoilApi::class)
@Composable
fun ShanimeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontSizeConfig: FontSizeConfig = FontSizeConfig.NORMAL,
    preferredLanguage: PreferredLanguage = PreferredLanguage.ENGLISH,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        ShanimeDarkColorScheme
    } else {
        ShanimeLightColorScheme
    }
    val fontFamily = when (preferredLanguage) {
        PreferredLanguage.ENGLISH -> EnglishFontFamily
        PreferredLanguage.KHMER -> KhmerFontFamily
    }
    val typography = when (fontSizeConfig) {
        FontSizeConfig.NORMAL -> {
            ShanimeTypography(
                navigationTitle = ShanimeTextStyle.NORMAL.navigationTitle.withRespectiveFontFamily(fontFamily = fontFamily),
                titleMedium = ShanimeTextStyle.NORMAL.titleMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                titleSmall = ShanimeTextStyle.NORMAL.titleSmall.withRespectiveFontFamily(fontFamily = fontFamily),
                bodyMedium = ShanimeTextStyle.NORMAL.bodyMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                bodySmall = ShanimeTextStyle.NORMAL.bodySmall.withRespectiveFontFamily(fontFamily = fontFamily),
            )
        }

        FontSizeConfig.MEDIUM -> {
            ShanimeTypography(
                navigationTitle = ShanimeTextStyle.MEDIUM.navigationTitle.withRespectiveFontFamily(fontFamily = fontFamily),
                titleMedium = ShanimeTextStyle.MEDIUM.titleMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                titleSmall = ShanimeTextStyle.MEDIUM.titleSmall.withRespectiveFontFamily(fontFamily = fontFamily),
                bodyMedium = ShanimeTextStyle.MEDIUM.bodyMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                bodySmall = ShanimeTextStyle.MEDIUM.bodySmall.withRespectiveFontFamily(fontFamily = fontFamily),
            )
        }

        FontSizeConfig.LARGE -> {
            ShanimeTypography(
                navigationTitle = ShanimeTextStyle.LARGE.navigationTitle.withRespectiveFontFamily(fontFamily = fontFamily),
                titleMedium = ShanimeTextStyle.LARGE.titleMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                titleSmall = ShanimeTextStyle.LARGE.titleSmall.withRespectiveFontFamily(fontFamily = fontFamily),
                bodyMedium = ShanimeTextStyle.LARGE.bodyMedium.withRespectiveFontFamily(fontFamily = fontFamily),
                bodySmall = ShanimeTextStyle.LARGE.bodySmall.withRespectiveFontFamily(fontFamily = fontFamily),
            )
        }
    }

    val isInPreviewMode = LocalInspectionMode.current

    if (isInPreviewMode) {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CompositionLocalProvider(
                    LocalShanimeColor provides colorScheme.switch(),
                    LocalShanimeTypography provides typography,
                    LocalTextSelectionColors provides TextSelectionColors(
                        handleColor = colorScheme.primary,
                        backgroundColor = colorScheme.secondary,
                    ),
                    LocalNavAnimatedVisibilityScope provides this,
                    LocalSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler { imageLoader, request ->
                        val newRequest = request.newBuilder().data(R.drawable.image_preview_placeholder).build()
                        when (val result = imageLoader.execute(newRequest)) {
                            is SuccessResult -> Success(result.image.asPainter(newRequest.context), result)
                            is ErrorResult -> Error(result.image?.asPainter(newRequest.context), result)
                        }
                    },
                ) {
                    content()
                }
            }
        }
    } else {
        CompositionLocalProvider(
            LocalShanimeColor provides colorScheme.switch(),
            LocalShanimeTypography provides typography,
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = colorScheme.primary,
                backgroundColor = colorScheme.secondary,
            ),
        ) {
            content()
        }
    }
}

@Composable
private fun animateColor(targetValue: Color) =
    animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 500),
        label = "",
    ).value

@Composable
private fun AppColorScheme.switch() = copy(
    primary = animateColor(primary),
    secondary = animateColor(secondary),
    background = animateColor(background),
    surface = animateColor(surface),
    textPrimary = animateColor(textPrimary),
    textSecondary = animateColor(textSecondary),
    shimmer = shimmer,
)

private fun TextStyle.withRespectiveFontFamily(fontFamily: FontFamily) = copy(
    fontFamily = fontFamily,
)

object ShanimeTheme {
    val colors: AppColorScheme
        @Composable
        get() = LocalShanimeColor.current

    val typography: ShanimeTypography
        @Composable
        get() = LocalShanimeTypography.current
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun getSharedTransitionScope() = LocalSharedTransitionScope.current

@Composable
fun getNavAnimatedVisibilityScope() = LocalNavAnimatedVisibilityScope.current

val WindowInsets.Companion.none: WindowInsets
    @Stable
    get() = WindowInsets(0, 0, 0, 0)
