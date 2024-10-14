package com.core.common.blur

const val BLURRED_PLACEHOLDER_HASH = "L1R{#??b~q-;~qfQofj[~qWB00t7"

val blurredBitmap = BlurHashDecoder.decode(
    blurHash = BLURRED_PLACEHOLDER_HASH,
    width = 20,
    height = 12,
)
