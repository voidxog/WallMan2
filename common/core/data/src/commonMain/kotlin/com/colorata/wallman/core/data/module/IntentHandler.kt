package com.voidxog.wallman2.core.data.module

import com.voidxog.wallman2.core.data.Coordinates
import kotlin.reflect.KClass

interface IntentHandler {
    fun goToUrl(url: String)

    fun <T : Any> goToActivity(activity: KClass<T>, data: Map<String, String> = mapOf())

    fun goToLiveWallpaper(packageName: String, serviceName: String)

    fun goToMaps(coordinates: Coordinates)

    fun goToPermissionPage(page: PermissionPage)

    fun exit()

}

enum class PermissionPage {
    InstallUnknownApps
}
