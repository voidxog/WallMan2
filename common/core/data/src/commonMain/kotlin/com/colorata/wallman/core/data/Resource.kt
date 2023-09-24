package com.colorata.wallman.core.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter

@Immutable
sealed interface Resource {
    enum class Drawable: Resource {
        LOGO,
        CIRCLE,
        CLEVER,
        SCALLOP,
        SQUARE
    }
}

interface BitmapAssetStore {
    fun get(assetName: String): ImageBitmap
}

val LocalBitmapAssetStore =
    compositionLocalOf<BitmapAssetStore> { error("No LocalBitmapAssetStore is provided") }


@Composable
expect fun painterDrawable(resource: Resource.Drawable): Painter

@Composable
expect fun bitmapAsset(assetName: String): ImageBitmap