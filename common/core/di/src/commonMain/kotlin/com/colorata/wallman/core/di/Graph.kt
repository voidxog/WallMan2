package com.voidxog.wallman2.core.di

import androidx.compose.runtime.compositionLocalOf
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.widget.api.WidgetModule

interface Graph {
    val coreModule: CoreModule
    val wallpapersModule: WallpapersModule
    val widgetModule: WidgetModule
}

val LocalGraph = compositionLocalOf<Graph> { error("No Graph provided") }
