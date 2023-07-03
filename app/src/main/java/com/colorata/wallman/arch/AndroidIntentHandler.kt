package com.colorata.wallman.arch

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.colorata.wallman.wallpaper.Coordinates
import kotlin.reflect.KClass

class AndroidIntentHandler(private val context: Context) : IntentHandler {
    override fun goToUrl(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.start()
    }

    override fun <T : Any> goToActivity(activity: KClass<T>) {
        Intent(context, activity.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.start()
    }

    override fun goToLiveWallpaper(packageName: String, serviceName: String) {
        val component = ComponentName(
            packageName,
            serviceName
        )
        Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, component)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            .start()
    }

    override fun goToMaps(coordinates: Coordinates) {
        val url =
            when (coordinates) {
                is Coordinates.ExactCoordinates ->
                    "geo:${coordinates.latitude},${coordinates.longitude}?z=16"

                is Coordinates.AddressCoordinates ->
                    "geo:0,0?q=${coordinates.address.replace(" ", "+")}"
            }
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        ).start()
    }

    private fun Intent.start() = context.startActivity(this)
}