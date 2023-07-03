package com.colorata.wallman.arch.widget

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.colorata.wallman.arch.graph
import com.colorata.wallman.ui.widgets.DailyAppWidget
import com.colorata.wallman.wallpaper.goToLiveWallpaper
import com.colorata.wallman.wallpaper.supportsDynamicWallpapers

@Keep
class DailyDynamicWallpaperCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val graph = context.graph
        val intentHandler = graph.intentHandler
        val mainRepo = graph.mainRepo
        val widget = WidgetState(context, PreferencesGlanceStateDefinition, glanceId)
        widget.update {
            val currentWallpaperHashcode =
                this[intPreferencesKey(DailyAppWidget.wallpaperHashcode.name)]
            if (currentWallpaperHashcode != null) {
                val wallpaper = DailyAppWidget.run {
                    mainRepo.wallpapers.getDynamicWallpaperByHashCode(currentWallpaperHashcode)
                }
                if (wallpaper != null) intentHandler.goToLiveWallpaper(wallpaper)
            }
            updateWallpaper(context)
        }
    }

}

fun MutablePreferences.updateWallpaper(context: Context) {
    val mainRepo = context.graph.mainRepo
    val randomWallpaper =
        mainRepo.wallpapers.filter { it.supportsDynamicWallpapers() }.random()
    this[intPreferencesKey(DailyAppWidget.wallpaperHashcode.name)] =
        randomWallpaper.dynamicWallpapers.random().hashCode()
}