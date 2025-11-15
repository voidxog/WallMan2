package com.voidxog.wallman2.core.ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import com.voidxog.wallman2.core.data.BitmapAssetStore
import com.voidxog.wallman2.core.data.LocalBitmapAssetStore
import com.voidxog.wallman2.core.ui.R

private class PreviewBitmapAssetStore(private val context: Context): BitmapAssetStore {
    override fun get(assetName: String): ImageBitmap {
        return context.getDrawable(R.drawable.image_preview)?.toBitmap()?.asImageBitmap() ?: error("Cannot load default bitmap")
    }
}
@Composable
fun ProvideBitmapAssetStorePreview(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val store = remember { PreviewBitmapAssetStore(context) }
    CompositionLocalProvider(LocalBitmapAssetStore provides store) {
        content()
    }
}
