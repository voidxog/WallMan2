package com.colorata.wallman.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.di.loadables
import com.colorata.wallman.core.impl.*
import com.colorata.wallman.wallpapers.ui.WallpaperDetailsScreen

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
        graph.apply {
            permissionHandler = PermissionHandlerImpl(this@MainActivity)
            intentHandler = IntentHandlerImpl(this@MainActivity)
            appsProvider = this@MainActivity.appsProvider
            systemProvider = this@MainActivity.systemProvider
            downloadHandler = this@MainActivity.downloadHandler
        }

        setContent {
            CompositionLocalProvider(LocalGraph provides graph) {
                App()
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