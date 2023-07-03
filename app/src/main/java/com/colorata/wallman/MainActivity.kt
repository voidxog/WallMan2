package com.colorata.wallman

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.colorata.wallman.arch.AndroidAppsProvider
import com.colorata.wallman.arch.AndroidDownloadHandler
import com.colorata.wallman.arch.AndroidIntentHandler
import com.colorata.wallman.arch.AndroidPermissionHandler
import com.colorata.wallman.arch.AndroidSystemProvider
import com.colorata.wallman.arch.LocalGraph
import com.colorata.wallman.arch.WallManApp
import com.colorata.wallman.ui.screens.DailyWidgetConfigurationScreen
import com.colorata.wallman.ui.screens.Navigation
import com.colorata.wallman.ui.theme.WallManTheme
import com.colorata.wallman.ui.widgets.DailyAppWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val graph by lazy {
        (this.application as WallManApp).graph
    }
    private val scope by lazy { CoroutineScope(Dispatchers.IO) }
    private val systemProvider by lazy {
        AndroidSystemProvider(this, scope)
    }
    private val appsProvider by lazy {
        AndroidAppsProvider(this)
    }
    private val downloadHandler by lazy {
        AndroidDownloadHandler(this@MainActivity, scope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        graph.apply {
            permissionHandler = AndroidPermissionHandler(this@MainActivity)
            intentHandler = AndroidIntentHandler(this@MainActivity)
            appsProvider = this@MainActivity.appsProvider
            systemProvider = this@MainActivity.systemProvider
            downloadHandler = this@MainActivity.downloadHandler
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { false })
        }

        setResult(RESULT_CANCELED)
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
            CompositionLocalProvider(LocalGraph provides graph) {
                WallManTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (remember { appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID }) {
                            val scope = rememberCoroutineScope()
                            var resId: Int? by remember { mutableStateOf(null) }
                            LaunchedEffect(key1 = true) {
                                scope.launch {
                                    val manager = GlanceAppWidgetManager(this@MainActivity)
                                    val glanceIds =
                                        manager.getGlanceIds(DailyAppWidget::class.java).last()
                                    updateAppWidgetState(this@MainActivity, glanceIds) {
                                        it.apply {
                                            resId =
                                                this[intPreferencesKey(DailyAppWidget.shapeKey.name)]
                                                    ?: R.drawable.ic_clever
                                        }
                                    }
                                }
                            }

                            DailyWidgetConfigurationScreen(resId) { shape ->
                                scope.launch {
                                    val manager = GlanceAppWidgetManager(this@MainActivity)
                                    val glanceIds =
                                        manager.getGlanceIds(DailyAppWidget::class.java).last()
                                    updateAppWidgetState(this@MainActivity, glanceIds) {
                                        it.apply {
                                            this[intPreferencesKey(DailyAppWidget.shapeKey.name)] =
                                                shape
                                        }
                                    }
                                    DailyAppWidget().update(this@MainActivity, glanceIds)
                                    finish()
                                }
                            }
                        } else {
                            Navigation()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        systemProvider.loadResources()
        appsProvider.loadResources()
    }


    override fun onStop() {
        super.onStop()
        systemProvider.unloadResources()
        appsProvider.unloadResources()
    }

}