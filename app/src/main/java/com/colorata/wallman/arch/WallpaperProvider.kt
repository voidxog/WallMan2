package com.colorata.wallman.arch

import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

interface WallpaperProvider {
    data class LiveWallpaper(
        val packageName: String,
        val serviceName: String
    )

    fun currentLiveWallpaper(): Flow<LiveWallpaper?>

    fun installStaticWallpaper(path: String): Flow<Result<Unit>>
}

object NoopWallpaperProvider : WallpaperProvider {
    override fun currentLiveWallpaper(): Flow<WallpaperProvider.LiveWallpaper?> {
        return flow { }
    }

    override fun installStaticWallpaper(path: String): Flow<Result<Unit>> {
        return flow { }
    }
}

class AndroidWallpaperProvider(
    private val context: Context,
    scope: CoroutineScope
) : WallpaperProvider {
    private val wallpaperManager by lazy { WallpaperManager.getInstance(context) }
    private val _currentLiveWallpaper = MutableStateFlow<WallpaperProvider.LiveWallpaper?>(null)

    init {
        scope.launchIO({ it.printStackTrace() }) {
            while (isActive) {
                _currentLiveWallpaper.value = wallpaperManager.wallpaperInfo?.toLiveWallpaper()
                wallpaperManager
                delay(1000)
            }
        }
    }

    override fun currentLiveWallpaper(): Flow<WallpaperProvider.LiveWallpaper?> {
        return _currentLiveWallpaper
    }

    override fun installStaticWallpaper(path: String): Flow<Result<Unit>> {
        return flow {
            runCatching {
                val bitmap = BitmapFactory.decodeFile(path)
                wallpaperManager.setBitmap(bitmap)
            }.onSuccess { emit(Result.Success(Unit)) }.onFailure { emit(Result.Error(it)) }
        }
    }

    private fun WallpaperInfo.toLiveWallpaper() = WallpaperProvider.LiveWallpaper(packageName, serviceName)
}