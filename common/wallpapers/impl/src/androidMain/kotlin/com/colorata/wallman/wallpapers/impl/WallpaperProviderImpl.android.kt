package com.colorata.wallman.wallpapers.impl

import android.annotation.SuppressLint
import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.core.data.Result
import com.colorata.wallman.core.data.launchIO
import com.colorata.wallman.core.data.module.Logger
import com.colorata.wallman.core.data.module.throwable
import com.colorata.wallman.core.data.runResulting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

actual class WallpaperProviderImpl(
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
                wallpaperManager.setBitmap(
                    /* fullImage = */ bitmap,
                    /* visibleCropHint = */ null,
                    /* allowBackup = */ true,
                    /* which = */ WallpaperManager.FLAG_LOCK
                )
                emit(Result.Loading(0.5f))
                wallpaperManager.setBitmap(
                    /* fullImage = */ bitmap,
                    /* visibleCropHint = */ null,
                    /* allowBackup = */ true,
                    /* which = */ WallpaperManager.FLAG_SYSTEM
                )
                emit(Result.Loading(1f))
            }
            emit(result)
        }
    }

    private fun WallpaperInfo.toLiveWallpaper() =
        WallpaperProvider.LiveWallpaper(packageName, serviceName)
}