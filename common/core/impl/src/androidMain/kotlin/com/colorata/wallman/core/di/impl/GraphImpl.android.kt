package com.voidxog.wallman2.core.di.impl

import android.app.Application
import androidx.activity.ComponentActivity
import com.voidxog.wallman2.core.di.Graph
import com.voidxog.wallman2.core.impl.CoreModuleImpl
import com.voidxog.wallman2.core.impl.applyActivity
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.impl.AndroidWallpapersModuleImpl
import com.voidxog.wallman2.widget.impl.WidgetModuleImpl
import com.voidxog.wallman2.widget.impl.applyActivity

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
