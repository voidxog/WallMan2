package com.colorata.wallman.wallpapers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.colorata.wallman.core.data.Result
interface WallpaperProvider {
    data class LiveWallpaper(
        val packageName: String,
        val serviceName: String
    )

    fun currentLiveWallpaper(): Flow<LiveWallpaper?>

    fun installStaticWallpaper(path: String): Flow<Result<Unit>>

    object NoopWallpaperProvider : WallpaperProvider {
        override fun currentLiveWallpaper(): Flow<LiveWallpaper?> {
            return flow { }
        }

        override fun installStaticWallpaper(path: String): Flow<Result<Unit>> {
            return flow { }
        }
    }
}