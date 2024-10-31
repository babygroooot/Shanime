package com.core.designsystem.component.imageviewer

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.core.designsystem.component.dialog.EdgeToEdgeDialog
import com.core.designsystem.component.flick.FlickToDismiss
import com.core.designsystem.component.flick.FlickToDismissState
import com.core.designsystem.component.flick.rememberFlickToDismissState
import kotlinx.coroutines.delay
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState
import me.saket.telephoto.zoomable.rememberZoomableState

@Composable
fun ImageViewer(
    state: ImageViewerState,
    modifier: Modifier = Modifier,
) {
    if (state.imageIsShown) {
        EdgeToEdgeDialog(
            onDismissRequest = {
                state.hideImage()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
            ),
        ) {
            val zoomableState = rememberZoomableState(
                zoomSpec = ZoomSpec(maxZoomFactor = 10f),
            )
            val flickState = rememberFlickToDismissState(
                rotateOnDrag = false,
            )
            val imageState = rememberZoomableImageState(
                zoomableState = zoomableState,
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = when (flickState.gestureState) {
                    is FlickToDismissState.GestureState.Dismissed,
                    is FlickToDismissState.GestureState.Dismissing,
                    -> 0f

                    is FlickToDismissState.GestureState.Dragging -> if ((flickState.gestureState as FlickToDismissState.GestureState.Dragging).willDismissOnRelease) 0.5f else 1f

                    is FlickToDismissState.GestureState.Idle,
                    is FlickToDismissState.GestureState.Resetting,
                    -> 1f
                },
                label = "Background alpha",
                animationSpec = tween(durationMillis = 500),
            )
            val backgroundColor by remember {
                derivedStateOf {
                    Color.Black.copy(alpha = animatedAlpha)
                }
            }
            Box(
                modifier = modifier
                    .drawBehind {
                        drawRect(
                            color = backgroundColor,
                        )
                    },
            ) {
                FlickToDismiss(
                    state = flickState,
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    ZoomableAsyncImage(
                        state = imageState,
                        model = state.image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                    CloseScreenOnFlickDismissEffect(flickState = flickState)
                    LaunchedEffect(flickState.gestureState is FlickToDismissState.GestureState.Dragging) {
                        zoomableState.resetZoom()
                    }
                }
                IconButton(
                    onClick = {
                        state.hideImage()
                    },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(5.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(size = 30.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CloseScreenOnFlickDismissEffect(flickState: FlickToDismissState) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val gestureState = flickState.gestureState

    if (gestureState is FlickToDismissState.GestureState.Dismissing) {
        LaunchedEffect(Unit) {
            // Schedule an exit in advance because there is a slight delay from when an exit
            // navigation is issued to when the screen actually hides from the UI. Gotta think
            // of a better way to do this. Could the flick animation integrate with the navigation
            // framework's exit transition?
            delay(duration = gestureState.animationDuration / 2)
            backDispatcher.onBackPressed()
        }
    }
}
