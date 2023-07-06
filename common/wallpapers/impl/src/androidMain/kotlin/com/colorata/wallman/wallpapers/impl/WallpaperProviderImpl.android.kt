package com.colorata.wallman.wallpapers.impl

import android.annotation.SuppressLint
import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.core.data.Result
import com.colorata.wallman.core.data.launchIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

actual class WallpaperProviderImpl(private val context: Context, scope: CoroutineScope): WallpaperProvider {
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

    @SuppressLint("MissingPermission")
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