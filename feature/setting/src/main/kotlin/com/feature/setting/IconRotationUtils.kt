package com.feature.setting

import androidx.compose.ui.unit.Density
import kotlin.math.abs
import kotlin.math.sign

internal fun calculateAngle(offsetX: Float, density: Density, diff: Float): Float {
    val offsetInDp = with(density) { offsetX.toDp() }
    return offsetInDp.value * diff
}

internal fun Float.normalizeAngle(): Float = this % 360f

internal fun Float.findNextAngle(spinClockwise: Boolean): Float = if (spinClockwise) this - 180f else this + 180f

internal fun Float.findNearAngle(velocity: Float): Float {
    val velocityAddition = if (abs(velocity) > 1000) {
        90f * velocity.sign
    } else {
        0f
    }
    val normalizedAngle = this.normalizeAngle() + velocityAddition
    val minimalAngle = (this / 360f).toInt() * 360f
    return when {
        normalizedAngle in -90f..90f -> minimalAngle
        abs(normalizedAngle) >= 270f -> minimalAngle + 360f * this.sign
        abs(normalizedAngle) >= 90f -> minimalAngle + 180f * this.sign
        else -> 0f
    }
}
