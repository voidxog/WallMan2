package com.voidxog.wallman2.wallpapers

import com.voidxog.wallman2.core.data.module.CoreModule

interface WallpapersModule : CoreModule {
    val wallpaperProvider: WallpaperProvider

    val wallpaperManager: WallpaperManager

    val wallpapersRepository: WallpapersRepository
}

