package com.colorata.wallman.core.data

import com.colorata.wallman.core.*
import com.colorata.wallman.core.data.IntentHandler
import com.colorata.wallman.core.data.NavigationController
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