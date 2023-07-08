package com.colorata.wallman.core.di.impl

import android.app.Application
import com.colorata.wallman.core.*
import com.colorata.wallman.core.data.IntentHandler
import com.colorata.wallman.core.data.NavigationController
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.core.impl.ApplicationSettingsImpl
import com.colorata.wallman.core.impl.NavigationControllerImpl
import com.colorata.wallman.wallpapers.WallpaperManager
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.wallpapers.impl.WallpaperManagerImpl
import com.colorata.wallman.wallpapers.impl.WallpaperProviderImpl
import com.colorata.wallman.wallpapers.impl.WallpapersRepositoryImpl
import com.colorata.wallman.widget.api.EverydayWidgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual class GraphImpl(private val application: Application) : Graph {
    override val coroutineScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    override var permissionHandler: PermissionHandler = PermissionHandler.NoopPermissionHandler
    override var intentHandler: IntentHandler = IntentHandler.NoopIntentHandler
    override var systemProvider: SystemProvider = SystemProvider.NoopSystemProvider
    override var appsProvider: AppsProvider = AppsProvider.NoopAppsProvider
    override var downloadHandler: DownloadHandler = DownloadHandler.NoopDownloadHandler

    override val applicationSettings: ApplicationSettings by lazy {
        ApplicationSettingsImpl(application, coroutineScope)
    }
    override val navigationController: NavigationController by lazy {
        NavigationControllerImpl(application, coroutineScope)
    }
    override val wallpaperProvider: WallpaperProvider by lazy {
        WallpaperProviderImpl(application, coroutineScope)
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
        WallpapersRepositoryImpl()
    }

    override var everydayWidgetRepository: EverydayWidgetRepository =
        EverydayWidgetRepository.NoopEverydayWidgetRepository
}