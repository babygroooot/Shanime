package com.core.common.util

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setStatusBarAppearance(isLightAppearance: Boolean) {
    WindowCompat.getInsetsController(
        window,
        window.decorView,
    ).isAppearanceLightStatusBars = isLightAppearance
}
