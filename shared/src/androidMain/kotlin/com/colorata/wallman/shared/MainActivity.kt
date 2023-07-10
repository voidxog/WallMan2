package com.colorata.wallman.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.module.loadables
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.di.impl.applyActivity
import com.colorata.wallman.wallpapers.MainDestination
import com.colorata.wallman.widget.api.ShapePickerDestination
import com.colorata.wallman.widget.impl.isWidgetConfiguration

class MainActivity : ComponentActivity() {
    private val graph by lazy {
        (application as WallManApp).graph
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isShapeConfiguration = isWidgetConfiguration()

        graph.applyActivity(this)

        val startDestination =
            if (isShapeConfiguration) Destinations.ShapePickerDestination() else Destinations.MainDestination()

        setContent {
            CompositionLocalProvider(LocalGraph provides graph) {
                App(startDestination)
            }
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { false })
        }
    }

    override fun onStart() {
        super.onStart()
        graph.coreModule.loadables.forEach { it.load() }
    }

    override fun onStop() {
        super.onStop()
        graph.coreModule.loadables.forEach { it.unload() }
    }
}