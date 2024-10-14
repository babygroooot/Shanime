package com.babygroot.shanime

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.asImage
import coil3.request.crossfade
import com.core.common.blur.blurredBitmap
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication :
    Application(),
    SingletonImageLoader.Factory {

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        val imageLoader = ImageLoader.Builder(context.applicationContext)
            .crossfade(enable = true)
            .placeholder(blurredBitmap?.asImage())
        return imageLoader.build()
    }
}
