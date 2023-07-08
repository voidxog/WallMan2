package com.colorata.wallman.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.launchIO
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.di.loadables
import com.colorata.wallman.core.impl.*
import com.colorata.wallman.wallpapers.MainDestination
import com.colorata.wallman.widget.api.ShapePickerDestination
import com.colorata.wallman.widget.impl.EverydayWidgetRepositoryImpl
import com.colorata.wallman.widget.impl.currentWidgetShapeId
import com.colorata.wallman.widget.impl.isWidgetConfiguration
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    private val graph by lazy {
        (application as WallManApp).graph
    }
    private val systemProvider by lazy {
        SystemProviderImpl(this, graph.coroutineScope)
    }
    private val appsProvider by lazy {
        AppsProviderImpl(this)
    }
    private val downloadHandler by lazy {
        DownloadHandlerImpl(this, graph.coroutineScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isShapeConfiguration = isWidgetConfiguration()

        graph.apply {
            permissionHandler = PermissionHandlerImpl(this@MainActivity)
            intentHandler = IntentHandlerImpl(this@MainActivity)
            appsProvider = this@MainActivity.appsProvider
            systemProvider = this@MainActivity.systemProvider
            downloadHandler = this@MainActivity.downloadHandler
            if (isShapeConfiguration) lifecycleScope.launchIO({ it.printStackTrace() }) {
                currentWidgetShapeId().collect {
                    everydayWidgetRepository = EverydayWidgetRepositoryImpl(it, this@MainActivity)
                }
            }
        }

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
        graph.loadables.forEach { it.load() }
    }

    override fun onStop() {
        super.onStop()
        graph.loadables.forEach { it.unload() }
    }
}