package com.core.designsystem.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * A composable that animates a transition between a moon and a sun shape.
 *
 * @param isDark A boolean indicating whether the current shape is a moon.
 * @param color The color of the content.
 * @param modifier The modifier to be applied to the layout.
 * @param animationSpec The animation specification for the transition animation.
 */
@Composable
fun ThemeSwitcher(
    isDark: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(400),
) {
    val progress by animateFloatAsState(
        targetValue = if (isDark) 1f else 0f,
        animationSpec = animationSpec,
        label = "Theme switcher progress",
    )

    Canvas(
        modifier = modifier
            .size(24.dp)
            .aspectRatio(1f),
    ) {
        val width = size.width
        val height = size.height
        val baseRadius = width * 0.25f
        val extraRadius = width * 0.2f * progress
        val radius = baseRadius + extraRadius

        rotate(180f * (1 - progress)) {
            val raysProgress = if (progress < 0.5f) (progress / 0.85f) else 0f
            drawRays(
                color = color,
                alpha = if (progress < 0.5) 1f else 0f,
                radius = (radius * 1.5f) * (1f - raysProgress),
                rayWidth = radius * 0.3f,
                rayLength = radius * 0.2f,
            )

            drawMoonToSun(radius, progress, color)
        }

        val starProgress = if (progress > 0.8f) ((progress - 0.8f) / 0.2f) else 0f
        drawStar(
            color = color,
            centerOffset = Offset(width * 0.4f, height * 0.4f),
            radius = (height * 0.05f) * starProgress,
            alpha = starProgress,
        )
        drawStar(
            color = color,
            centerOffset = Offset(width * 0.2f, height * 0.2f),
            radius = (height * 0.1f) * starProgress,
            alpha = starProgress,
        )
    }
}

/**
 * Draws the transition from a moon shape to a sun shape based on the progress.
 *
 * @param radius The radius of the main circle.
 * @param progress The animation progress value ranging from 0 (moon) to 1 (sun).
 * @param color The color of the shapes.
 */
private fun DrawScope.drawMoonToSun(radius: Float, progress: Float, color: Color) {
    val mainCircle = Path().apply {
        addOval(Rect(center, radius))
    }

    val initialOffset = center - Offset(radius * 2.3f, radius * 2.3f)
    val offset = (radius * 1.8f) * progress
    val subtractCircle = Path().apply {
        addOval(Rect(initialOffset + Offset(offset, offset), radius))
    }

    val moonToSunPath = Path().apply {
        op(mainCircle, subtractCircle, PathOperation.Difference)
    }
    drawPath(moonToSunPath, color)
}

/**
 * Draws a star at the specified offset with given parameters.
 *
 * @param color The color of the star.
 * @param centerOffset The offset where the center of the star will be drawn.
 * @param radius The radius of the star.
 * @param alpha The alpha (opacity) of the star.
 */
private fun DrawScope.drawStar(
    color: Color,
    centerOffset: Offset,
    radius: Float,
    alpha: Float = 1f,
) {
    val leverage = radius * 0.1f
    val starPath = Path().apply {
        moveTo(centerOffset.x - radius, centerOffset.y)
        quadraticTo(
            x1 = centerOffset.x - leverage,
            y1 = centerOffset.y - leverage,
            x2 = centerOffset.x,
            y2 = centerOffset.y - radius,
        )
        quadraticTo(
            x1 = centerOffset.x + leverage,
            y1 = centerOffset.y - leverage,
            x2 = centerOffset.x + radius,
            y2 = centerOffset.y,
        )
        quadraticTo(
            x1 = centerOffset.x + leverage,
            y1 = centerOffset.y + leverage,
            x2 = centerOffset.x,
            y2 = centerOffset.y + radius,
        )
        quadraticTo(
            x1 = centerOffset.x - leverage,
            y1 = centerOffset.y + leverage,
            x2 = centerOffset.x - radius,
            y2 = centerOffset.y,
        )
    }
    drawPath(starPath, color, alpha)
}

/**
 * Draws rays around a center point with given parameters.
 *
 * @param color The color of the rays.
 * @param radius The radius of the circle where the rays start.
 * @param rayWidth The width of each ray.
 * @param rayLength The length of each ray.
 * @param alpha The alpha (opacity) of the rays.
 * @param rayCount The number of rays to be drawn.
 */
private fun DrawScope.drawRays(
    color: Color,
    radius: Float,
    rayWidth: Float,
    rayLength: Float,
    alpha: Float = 1f,
    rayCount: Int = 8,
) {
    for (i in 0 until rayCount) {
        val angle = (2 * Math.PI * i / rayCount).toFloat()
        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)
        val endX = center.x + (radius + rayLength) * cos(angle)
        val endY = center.y + (radius + rayLength) * sin(angle)
        drawLine(
            color = color,
            alpha = alpha,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = rayWidth,
        )
    }
}
