package com.colorata.wallman.core.impl

import android.app.Activity
import android.app.Application
import com.colorata.wallman.core.data.module.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CoreModuleImpl(private val application: Application) : CoreModule {
    override val coroutineScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    override var permissionHandler: PermissionHandler = PermissionHandler.NoopPermissionHandler
    override val intentHandler by lazy { IntentHandlerImpl(application) }
    override val systemProvider: SystemProvider by lazy { SystemProviderImpl(application, coroutineScope) }
    override val appsProvider: AppsProvider by lazy { AppsProviderImpl(application) }
    override val downloadHandler: DownloadHandler by lazy { DownloadHandlerImpl(application, coroutineScope) }

    override val applicationSettings: ApplicationSettings by lazy {
        ApplicationSettingsImpl(application, coroutineScope)
    }
    override val navigationController: NavigationController by lazy {
        NavigationControllerImpl()
    }
}

fun CoreModuleImpl.applyActivity(activity: Activity) {
    permissionHandler = PermissionHandlerImpl(activity)
    intentHandler.setActivityContext(activity)
}