package com.colorata.wallman.core.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.data.module.LocalCoreModule
import com.colorata.wallman.wallpapers.LocalWallpapersModule
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.widget.api.LocalWidgetModule
import com.colorata.wallman.widget.api.WidgetModule

interface Graph {
    val coreModule: CoreModule
    val wallpapersModule: WallpapersModule
    val widgetModule: WidgetModule
}

val LocalGraph = compositionLocalOf<Graph> { error("No Graph provided") }

@Composable
fun ProvideGraphModules(content: @Composable () -> Unit) {
    val graph = LocalGraph.current
    CompositionLocalProvider(
        LocalCoreModule provides graph.coreModule,
        LocalWallpapersModule provides graph.wallpapersModule,
        LocalWidgetModule provides graph.widgetModule
    ) {
        content()
    }
}