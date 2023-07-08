package com.colorata.wallman.core.di

import androidx.compose.runtime.compositionLocalOf
import com.colorata.wallman.core.*
import com.colorata.wallman.core.data.IntentHandler
import com.colorata.wallman.core.data.Loadable
import com.colorata.wallman.core.data.NavigationController
import com.colorata.wallman.wallpapers.WallpaperManager
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.widget.api.EverydayWidgetRepository
import kotlinx.coroutines.CoroutineScope

interface Graph {
    val coroutineScope: CoroutineScope
    val permissionHandler: PermissionHandler
    val intentHandler: IntentHandler
    val systemProvider: SystemProvider
    val appsProvider: AppsProvider
    val downloadHandler: DownloadHandler
    val applicationSettings: ApplicationSettings
    val navigationController: NavigationController

    val wallpaperProvider: WallpaperProvider

    val wallpaperManager: WallpaperManager

    val wallpapersRepository: WallpapersRepository

    val everydayWidgetRepository: EverydayWidgetRepository
}

val LocalGraph = compositionLocalOf<Graph> { error("No Graph provided") }

val Graph.loadables: List<Loadable>
    get() = listOf(systemProvider, appsProvider)
