package com.colorata.wallman.core.data

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
actual fun painterDrawable(resource: Resource.Drawable): Painter {
    val id = remember(resource) { mapResourceToId(resource) }
    return painterResource(id)
}

private fun mapResourceToId(resource: Resource.Drawable): Int =
    // TODO: Add actual mapping
    when (resource) {
        Resource.Drawable.CLEVER -> androidx.core.R.drawable.ic_call_answer
        Resource.Drawable.CIRCLE -> androidx.core.R.drawable.ic_call_answer
        Resource.Drawable.LOGO -> androidx.core.R.drawable.ic_call_answer
        Resource.Drawable.SCALLOP -> androidx.core.R.drawable.ic_call_answer
        Resource.Drawable.SQUARE -> androidx.core.R.drawable.ic_call_answer
    }

actual class BitmapAssetStore(
    private val context: Context,
    private val scope: CoroutineScope,
    private val defaultColor: Color
) {
    private val lock = Any()
    private val assets = mutableMapOf<String, ImageBitmap>()
    actual fun get(assetName: String): ImageBitmap {
        val asset = assets[assetName]
        if (asset == null) {
            val notCached = getNotCached(assetName)
            scope.launch {
                synchronized(lock) {
                    assets[assetName] = notCached
                }
            }
            return notCached
        }
        return asset
    }

    private fun getNotCached(assetName: String): ImageBitmap {
        val fullPath =
            context.assets.list("")?.find { assetName in it.split(".", "/") }
                ?: error("Asset '$assetName' not found")

        return context.assets.open(fullPath)
            .use { BitmapFactory.decodeStream(it) }
            .asImageBitmap()
    }
}

@Composable
fun ProvideBitmapAssetStore(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val color = MaterialTheme.colorScheme.surfaceVariant
    val store = remember { BitmapAssetStore(context, scope, color) }
    CompositionLocalProvider(LocalBitmapAssetStore provides store) {
        content()
    }
}


@Composable
actual fun bitmapAsset(assetName: String): ImageBitmap {
    val store = LocalBitmapAssetStore.current
    return remember(assetName) { store.get(assetName) }
}