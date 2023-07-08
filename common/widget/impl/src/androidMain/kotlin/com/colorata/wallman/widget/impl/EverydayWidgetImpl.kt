package com.colorata.wallman.widget.impl

import androidx.datastore.preferences.core.intPreferencesKey
import com.colorata.wallman.core.data.IntentHandler
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import com.colorata.wallman.widget.api.EverydayWidget
import com.colorata.wallman.widget.api.ShapeConfiguration
import com.colorata.wallman.widget.api.WidgetState

class EverydayWidgetImpl(
    private val intentHandler: IntentHandler,
    private val wallpapersRepository: WallpapersRepository,
    private val shapeConfigurations: List<ShapeConfiguration>
) : EverydayWidget {

    private val widget by lazy {
        createEverydayWidgetContent()
    }

    override fun updateRandom(widgetState: WidgetState) {
        widgetState.update(widget) {
            val mainRepo = wallpapersRepository
            val randomWallpaper =
                mainRepo.wallpapers.filter { it.supportsDynamicWallpapers() }.random()

            this[intPreferencesKey(EverydayWidget.wallpaperHashcode)] =
                randomWallpaper.dynamicWallpapers.random().hashCode()
        }
    }

    override fun goToLiveWallpaper(wallpaper: WallpaperProvider.LiveWallpaper) {
        intentHandler.goToLiveWallpaper(wallpaper.packageName, wallpaper.serviceName)
    }

    override fun updateShape(shapeConfiguration: ShapeConfiguration) {
//        widgetState.update(widget) {
//            this[intPreferencesKey(EverydayWidget.shapeKey)] = shapeConfiguration.resId
//        }
    }

    override fun selectedShape(widgetState: WidgetState): ShapeConfiguration? {
        var shapeConfiguration: ShapeConfiguration? = null
        widgetState.update(widget) {
            val drawable = this[intPreferencesKey(EverydayWidget.shapeKey)]
            shapeConfiguration = if (drawable != null) shapeConfigurations.first { it.resId == drawable } else null
        }
        return shapeConfiguration
    }
}