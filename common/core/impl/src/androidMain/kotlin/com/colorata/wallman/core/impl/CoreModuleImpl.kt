package com.colorata.wallman.core.impl

import android.app.Activity
import android.app.Application
import com.colorata.wallman.core.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CoreModuleImpl(private val application: Application) : CoreModule {
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
}

fun CoreModuleImpl.applyActivity(activity: Activity) {
    permissionHandler = PermissionHandlerImpl(activity)
    intentHandler = IntentHandlerImpl(activity)
    appsProvider = AppsProviderImpl(activity)
    systemProvider = SystemProviderImpl(activity, coroutineScope)
    downloadHandler = DownloadHandlerImpl(activity, coroutineScope)
}