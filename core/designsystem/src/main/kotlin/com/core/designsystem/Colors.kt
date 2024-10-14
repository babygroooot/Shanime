package com.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorScheme(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val shimmer: List<Color>,
)

internal val ShanimeLightColorScheme = AppColorScheme(
    primary = Color(0xFF06C149),
    secondary = Color(0x6606C149),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    textPrimary = Color(0xFF000000),
    textSecondary = Color(0xFF3F3F3F),
    shimmer = listOf(
        Color(0xFFEEEEEE),
        Color(0xFFE9E9E9),
        Color(0xFFDDDDDD),
    ),
)

internal val ShanimeDarkColorScheme = AppColorScheme(
    primary = Color(0xFF06C149),
    secondary = Color(0xB206C149),
    background = Color(0xFF191A1F),
    surface = Color(0xFF1F222B),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFACACAC),
    shimmer = listOf(
        Color(0xFF35383f),
        Color(0xFF494b52),
        Color(0xFF5d5f65),
    ),
)
