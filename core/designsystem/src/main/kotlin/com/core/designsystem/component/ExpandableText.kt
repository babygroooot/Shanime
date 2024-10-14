package com.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize

@Composable
fun ExpandableText(
    text: String,
    truncateText: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 3,
    textStyle: TextStyle = TextStyle.Default,
    truncateTextStyle: TextStyle = TextStyle.Default,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var truncatedTextSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    LaunchedEffect(truncatedTextSize, textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect
        lastCharIndex = getLastTextChar(truncateTextSize = truncatedTextSize, textLayoutResult = textLayoutResult!!, maxLines = maxLines) ?: 0
    }
    Text(
        text = buildAnnotatedString {
            if (clickable) {
                if (isExpanded) {
                    withStyle(
                        style = textStyle.toSpanStyle(),
                    ) {
                        append(text = text)
                    }
                } else {
                    withStyle(
                        style = textStyle.toSpanStyle(),
                    ) {
                        val shortedText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(n = truncateText.length)
                            .dropLastWhile {
                                Character.isWhitespace(it) || it == '.'
                            }
                        append(text = shortedText)
                    }
                    withStyle(
                        style = truncateTextStyle.toSpanStyle(),
                    ) {
                        append(text = truncateText)
                    }
                }
            } else {
                withStyle(
                    style = textStyle.toSpanStyle(),
                ) {
                    append(
                        text = text,
                    )
                }
            }
        },
        onTextLayout = { result ->
            if (isExpanded.not() && result.hasVisualOverflow) {
                clickable = true
                if (textLayoutResult != null) return@Text
                textLayoutResult = result
            }
        },
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
        modifier = modifier
            .clickable(enabled = clickable, interactionSource = null, indication = null) {
                isExpanded = isExpanded.not()
            },
    )
    Text(
        text = truncateText,
        style = truncateTextStyle,
        onTextLayout = { result ->
            truncatedTextSize = result.size
        },
        modifier = Modifier.alpha(0f),
    )
}

fun getLastTextChar(
    truncateTextSize: IntSize,
    textLayoutResult: TextLayoutResult,
    maxLines: Int,
): Int? {
    val lastLineIndex = maxLines - 1
    val ellipsisWidth = truncateTextSize.width
    val lineLength = textLayoutResult.getLineEnd(lineIndex = lastLineIndex, visibleEnd = false)
    val lineEndX = textLayoutResult.getLineRight(lineIndex = lastLineIndex)

    for (charIndex in lineLength downTo 1) {
        val charRect = textLayoutResult.getBoundingBox(offset = charIndex)

        /** The space that has been created for the ellipsis to fit into */
        val ellipsisAvailableWidth = lineEndX - charRect.left
        if (ellipsisAvailableWidth > ellipsisWidth) {
            return charIndex
        }
    }
    return null
}
