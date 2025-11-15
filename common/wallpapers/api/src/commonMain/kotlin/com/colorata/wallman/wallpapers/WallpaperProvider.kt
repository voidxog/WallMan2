package com.voidxog.wallman2.wallpapers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.voidxog.wallman2.core.data.Result
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
