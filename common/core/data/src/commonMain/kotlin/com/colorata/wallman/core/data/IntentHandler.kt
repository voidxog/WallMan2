package com.colorata.wallman.core.data

import kotlin.reflect.KClass

interface IntentHandler {
    fun goToUrl(url: String)

    fun <T : Any> goToActivity(activity: KClass<T>)

    fun goToLiveWallpaper(packageName: String, serviceName: String)

    fun goToMaps(coordinates: Coordinates)


    object NoopIntentHandler : IntentHandler {
        override fun goToUrl(url: String) {}

        override fun <T : Any> goToActivity(activity: KClass<T>) {}

        override fun goToLiveWallpaper(packageName: String, serviceName: String) {}

        override fun goToMaps(coordinates: Coordinates) {}
    }
}