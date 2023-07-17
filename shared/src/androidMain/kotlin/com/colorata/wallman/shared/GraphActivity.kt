package com.colorata.wallman.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.colorata.wallman.core.data.module.loadables
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.di.impl.applyActivity
import com.colorata.wallman.core.ui.theme.WallManTheme

abstract class GraphActivity : ComponentActivity() {

    @Composable
    abstract fun Content()
    private val graph by lazy {
        (application as WallManApp).graph
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        graph.applyActivity(this)

        setContent {
            CompositionLocalProvider(LocalGraph provides graph) {
                WallManTheme {
                    Content()
                }
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