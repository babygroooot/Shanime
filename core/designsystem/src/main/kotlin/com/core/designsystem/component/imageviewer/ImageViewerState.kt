package com.core.designsystem.component.imageviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

interface ImageViewerState {
    var imageIsShown: Boolean

    var image: String

    fun viewImage(image: String)

    fun hideImage()
}

internal class ImageViewerStateImpl : ImageViewerState {

    override var imageIsShown: Boolean by mutableStateOf(false)

    override var image: String by mutableStateOf("")

    override fun viewImage(image: String) {
        imageIsShown = true
        this.image = image
    }

    override fun hideImage() {
        imageIsShown = false
        this.image = ""
    }

    companion object {
        val Saver: Saver<ImageViewerState, *> = Saver(
            save = { it.image to it.imageIsShown },
            restore = {
                ImageViewerStateImpl().also { state ->
                    state.image = it.first
                    state.imageIsShown = it.second
                }
            },
        )
    }
}

@Composable
fun rememberImageViewerState() = rememberSaveable(
    saver = ImageViewerStateImpl.Saver,
) {
    ImageViewerStateImpl()
}
