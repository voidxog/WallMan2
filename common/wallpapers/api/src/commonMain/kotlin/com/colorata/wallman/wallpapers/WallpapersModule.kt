package com.colorata.wallman.wallpapers

import androidx.compose.runtime.compositionLocalOf
import com.colorata.wallman.core.data.module.CoreModule

interface WallpapersModule : CoreModule {
    val wallpaperProvider: WallpaperProvider

    val wallpaperManager: WallpaperManager

    val wallpapersRepository: WallpapersRepository
}

val LocalWallpapersModule = compositionLocalOf<WallpapersModule> { error("WallpapersModule is not provided") }