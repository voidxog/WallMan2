package com.colorata.wallman.shared

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.wallman.core.data.Animation
import com.colorata.wallman.core.data.DurationSpec
import com.colorata.wallman.core.data.EasingSpec
import com.colorata.wallman.core.data.LocalAnimation
import com.colorata.wallman.core.data.module.loadables
import com.colorata.wallman.core.data.module.throwable
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.di.impl.applyActivity
import com.colorata.wallman.core.impl.applyWindowSize
import com.colorata.wallman.core.ui.theme.WallManTheme
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration

private const val RESTART_COUNT_KEY = "restart_count"

abstract class GraphActivity : ComponentActivity() {

    @Composable
    abstract fun Content()

    private val graph by lazy {
        (application as WallManApp).graph
    }

    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        graph.applyActivity(this)

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            graph.coreModule.logger.throwable(throwable)
            val restartCount = intent.extras?.getString(RESTART_COUNT_KEY)?.toIntOrNull() ?: 0
            println(restartCount)
            if (restartCount == 0) graph.coreModule.intentHandler.goToActivity(
                this::class,
                mapOf(RESTART_COUNT_KEY to "1")
            )
            finish()
        }

        setContent {
            val settings by graph.coreModule.applicationSettings.settings()
                .collectAsStateWithLifecycle()

            CompositionLocalProvider(
                LocalGraph provides graph,
                LocalAnimation provides remember(settings.animationType) {
                    Animation(
                        DurationSpec(),
                        EasingSpec(),
                        settings.animationType
                    )
                }
            ) {
                WallManTheme {
                    val windowSize = LocalWindowSizeConfiguration.current
                    remember(windowSize) { graph.coreModule.applyWindowSize(windowSize) }

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

    override fun onResume() {
        super.onResume()
        graph.coreModule.appsProvider.update()
    }

    override fun onStop() {
        super.onStop()
        graph.coreModule.loadables.forEach { it.unload() }
    }
}