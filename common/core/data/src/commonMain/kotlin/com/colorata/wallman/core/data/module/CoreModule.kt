package com.colorata.wallman.core.data.module

import androidx.compose.runtime.compositionLocalOf
import com.colorata.wallman.core.data.*
import kotlinx.coroutines.CoroutineScope

interface CoreModule {
    val coroutineScope: CoroutineScope
    val permissionHandler: PermissionHandler
    val intentHandler: IntentHandler
    val systemProvider: SystemProvider
    val appsProvider: AppsProvider
    val downloadHandler: DownloadHandler
    val applicationSettings: ApplicationSettings
    val navigationController: NavigationController
}

val CoreModule.loadables: List<Loadable>
    get() = listOf(systemProvider, appsProvider)

val LocalCoreModule = compositionLocalOf<CoreModule> { error("CoreModule is not provided") }