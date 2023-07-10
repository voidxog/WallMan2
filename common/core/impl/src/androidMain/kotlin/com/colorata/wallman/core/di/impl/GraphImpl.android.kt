package com.colorata.wallman.core.di.impl

import android.app.Activity
import android.app.Application
import androidx.activity.ComponentActivity
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.core.di.WallManApp
import com.colorata.wallman.core.impl.*
import com.colorata.wallman.wallpapers.WallpaperManager
import com.colorata.wallman.wallpapers.WallpaperProvider
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.wallpapers.impl.WallpaperManagerImpl
import com.colorata.wallman.wallpapers.impl.WallpaperProviderImpl
import com.colorata.wallman.wallpapers.impl.WallpapersModuleImpl
import com.colorata.wallman.wallpapers.impl.WallpapersRepositoryImpl
import com.colorata.wallman.widget.api.EverydayWidgetRepository
import com.colorata.wallman.widget.api.WidgetModule
import com.colorata.wallman.widget.impl.WidgetModuleImpl
import com.colorata.wallman.widget.impl.applyActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual class GraphImpl(private val application: Application) : Graph {
    override val coreModule by lazy { CoreModuleImpl(application) }

    override val wallpapersModule: WallpapersModule by lazy {
        WallpapersModuleImpl(coreModule, application)
    }

    override val widgetModule by lazy { WidgetModuleImpl(coreModule) }
}

fun GraphImpl.applyActivity(activity: ComponentActivity) {
    coreModule.applyActivity(activity)
    widgetModule.applyActivity(activity)
}