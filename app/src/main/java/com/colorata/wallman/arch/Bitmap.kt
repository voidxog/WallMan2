package com.colorata.wallman.arch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberBitmapFromResource(@DrawableRes id: Int): Bitmap {
    val context = LocalContext.current
    val bitmap = remember { BitmapFactory.decodeResource(context.resources, id) }
    DisposableEffect(key1 = Unit) {
        onDispose {
            bitmap.recycle()
        }
    }
    return bitmap
}

fun createBitmapFromResource(context: Context, @DrawableRes id: Int): Bitmap {
    return BitmapFactory.decodeResource(context.resources, id)
}

fun Bitmap.crop(predicate: (size: Size) -> Rect): Bitmap {

    val rect = predicate(Size(width.toFloat(), height.toFloat()))
    return Bitmap.createBitmap(
        this,
        rect.left.toInt(),
        rect.top.toInt(),
        rect.right.toInt() - rect.left.toInt(),
        rect.bottom.toInt() - rect.top.toInt()
    )
}

fun mergeBitmaps(
    first: Bitmap,
    second: Bitmap,
    width: Int = first.width + second.height,
    height: Int = first.height + second.height
): Bitmap {
    val bitmap = Bitmap.createBitmap(
        width, height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas()
    canvas.drawBitmap(
        first,
        null,
        android.graphics.Rect(0, 0, first.width, first.height),
        null
    )
    canvas.drawBitmap(
        second,
        null,
        android.graphics.Rect(
            second.width,
            second.height,
            first.width + second.width,
            first.height + second.height
        ),
        null
    )
    return bitmap
}

fun mergeBitmapsByHalf(first: Bitmap, second: Bitmap): Bitmap {
    val bitmap = Bitmap.createBitmap(
        first.width, first.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    canvas.drawBitmap(
        first.crop {
            Rect(0f, 0f, it.width / 2, it.height)
        },
        0f,
        0f,
        null
    )
    canvas.drawBitmap(
        second.crop {
            Rect(it.width / 2, 0f, it.width, it.height)
        },
        bitmap.width / 2f,
        0f,
        null
    )
    return bitmap
}