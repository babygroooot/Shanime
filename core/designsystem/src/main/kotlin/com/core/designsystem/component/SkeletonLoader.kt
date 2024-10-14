package com.core.designsystem.component

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

fun Modifier.shimmerEffect(
    colors: List<Color> = listOf(
        Color(0xFFEEEEEE),
        Color(0xFFE9E9E9),
        Color(0xFFDDDDDD),
    ),
    shape: Shape = RoundedCornerShape(8.dp),
    animationSpec: DurationBasedAnimationSpec<Float> = tween(durationMillis = 2_000),
) = composed {
    var composableSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val brushSize by remember {
        derivedStateOf {
            composableSize.width.toFloat()
        }
    }
    val density = LocalDensity.current
    val transition = rememberInfiniteTransition(label = "Shimmer Infinite Transition")
    val offset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * brushSize,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
            repeatMode = RepeatMode.Restart,
        ),
        label = "Infinite shimmer offset",
    )
    drawWithCache {
        onDrawWithContent {
            drawOutline(
                outline = shape.createOutline(size = composableSize.toSize(), layoutDirection = layoutDirection, density = density),
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(x = offset, y = offset),
                    end = Offset(x = offset + brushSize, y = offset + brushSize),
                    tileMode = TileMode.Mirror,
                ),
            )
        }
    }.onGloballyPositioned {
        composableSize = it.size
    }
}
