package com.voidxog.wallman2.wallpapers.impl

import android.annotation.SuppressLint
import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.voidxog.wallman2.core.data.Result
import com.voidxog.wallman2.core.data.launchIO
import com.voidxog.wallman2.core.data.module.Logger
import com.voidxog.wallman2.core.data.module.throwable
import com.voidxog.wallman2.core.data.runResulting
import com.voidxog.wallman2.wallpapers.WallpaperProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt


class AndroidWallpaperProviderImpl(
    private val context: Context,
    scope: CoroutineScope,
    private val logger: Logger
) : WallpaperProvider {
    private val wallpaperManager by lazy { WallpaperManager.getInstance(context) }
    private val _currentLiveWallpaper = MutableStateFlow<WallpaperProvider.LiveWallpaper?>(null)

    init {
        scope.launchIO({ logger.throwable(it) }) {
            while (isActive) {
                _currentLiveWallpaper.value = wallpaperManager.wallpaperInfo?.toLiveWallpaper()
                delay(1000)
            }
        }
    }

    override fun currentLiveWallpaper(): Flow<WallpaperProvider.LiveWallpaper?> {
        return _currentLiveWallpaper
    }

    @SuppressLint("MissingPermission")
    override fun installStaticWallpaper(path: String): Flow<Result<Unit>> {
        return flow {
            val result = runResulting {
                emit(Result.Loading(0f))
                val bitmap = BitmapFactory.decodeFile(path)

                // Setting wallpapers for both lock and home screen
                // because some OS set only to home screen by default
                val displayMetrics = displayMetrics()
                wallpaperManager.setBitmap(
                    /* fullImage = */bitmap.withAspectRatio(
                        displayMetrics.widthPixels,
                        displayMetrics.heightPixels
                    ),
                    /* visibleCropHint = */ null,
                    /* allowBackup = */ true,
                    /* which = */ WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                )
                emit(Result.Loading(1f))
            }
            emit(result)
        }
    }

    private fun Bitmap.withAspectRatio(targetWidth: Int, targetHeight: Int): Bitmap {
        return runCatching {
            actualWithAspectRatio(targetWidth, targetHeight)
        }.getOrElse { this }
    }

    private fun Bitmap.actualWithAspectRatio(targetWidth: Int, targetHeight: Int): Bitmap {
        val top: Int
        val bottom: Int
        val left: Int
        val right: Int

        val currentAspectRatio = width.toFloat() / height
        val targetAspectRatio = targetWidth.toFloat() / targetHeight

        if (currentAspectRatio >= targetAspectRatio) {
            top = 0
            bottom = height

            left = (width / 2 - targetAspectRatio * height / 2).roundToInt()
            right = (width / 2 + targetAspectRatio * height / 2).roundToInt()
        } else {
            left = 0
            right = width

            top = ((height / 2 - width / targetAspectRatio / 2).roundToInt())
            bottom = ((height / 2 + width / targetAspectRatio / 2).roundToInt())
        }

        val result = Bitmap.createBitmap(
            /* source = */ this,
            /* x = */ left,
            /* y = */ top,
            /* width = */ right - left,
            /* height = */ bottom - top
        )
        return Bitmap.createScaledBitmap(result, targetWidth, targetHeight, true)
    }

    private fun displayMetrics() = Resources.getSystem().displayMetrics

    private fun WallpaperInfo.toLiveWallpaper() =
        WallpaperProvider.LiveWallpaper(packageName, serviceName)
}
