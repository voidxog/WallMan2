package com.colorata.wallman.core.di.impl

import android.app.Application
import androidx.activity.ComponentActivity
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.core.impl.CoreModuleImpl
import com.colorata.wallman.core.impl.applyActivity
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.impl.AndroidWallpapersModuleImpl
import com.colorata.wallman.widget.impl.WidgetModuleImpl
import com.colorata.wallman.widget.impl.applyActivity

class GraphImpl(private val application: Application) : Graph {
    override val coreModule by lazy { CoreModuleImpl(application) }

    override val wallpapersModule: WallpapersModule by lazy {
        AndroidWallpapersModuleImpl(coreModule, application)
    }

    override val widgetModule by lazy { WidgetModuleImpl(coreModule) }
}

fun GraphImpl.applyActivity(activity: ComponentActivity) {
    coreModule.applyActivity(activity)
    widgetModule.applyActivity(activity)
}