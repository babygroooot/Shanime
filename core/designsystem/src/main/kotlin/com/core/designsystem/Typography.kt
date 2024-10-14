package com.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val EnglishFontFamily = FontFamily(
    Font(
        resId = R.font.urbanis_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.urbanist_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.urbanist_bold,
        weight = FontWeight.Bold,
    ),
)

internal val KhmerFontFamily = FontFamily(
    Font(
        resId = R.font.kantumruy_pro_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.kantumruy_pro_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.kantumruy_pro_bold,
        weight = FontWeight.Bold,
    ),
)

internal enum class ShanimeTextStyle(
    val navigationTitle: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
) {
    NORMAL(
        navigationTitle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = EnglishFontFamily,
        ),
        titleMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        titleSmall = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        bodyMedium = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
        bodySmall = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
    ),
    MEDIUM(
        navigationTitle = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = EnglishFontFamily,
        ),
        titleMedium = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        titleSmall = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
    ),
    LARGE(
        navigationTitle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = EnglishFontFamily,
        ),
        titleMedium = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        titleSmall = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = EnglishFontFamily,
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = EnglishFontFamily,
        ),
    ),
}

@Immutable
data class ShanimeTypography(
    val navigationTitle: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
)
