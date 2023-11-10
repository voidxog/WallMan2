package com.colorata.wallman.wallpapers.impl

import android.app.Application
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.wallpapers.WallpaperManager
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.WallpapersRepository

class AndroidWallpapersModuleImpl(
    coreModule: CoreModule,
    application: Application
) : WallpapersModule,
    CoreModule by coreModule {
    override val wallpaperProvider: WallpaperProvider by lazy {
        AndroidWallpaperProviderImpl(application, coroutineScope, logger)
    }
    override val wallpaperManager: WallpaperManager by lazy {
        WallpaperManagerImpl(
            systemProvider,
            appsProvider,
            downloadHandler,
            applicationSettings,
            wallpaperProvider,
            coroutineScope
        )
    }
    override val wallpapersRepository: WallpapersRepository by lazy {
        AndroidWallpapersRepositoryImpl()
    }
}