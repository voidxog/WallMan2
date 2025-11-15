package com.voidxog.wallman2.wallpapers.impl

import android.app.Application
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.wallpapers.WallpaperManager
import com.voidxog.wallman2.wallpapers.WallpaperProvider
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.WallpapersRepository

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
