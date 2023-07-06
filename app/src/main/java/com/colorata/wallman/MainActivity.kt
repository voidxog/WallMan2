package com.colorata.wallman

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.colorata.wallman.ui.screens.DailyWidgetConfigurationScreen
import com.colorata.wallman.ui.theme.WallManTheme
import com.colorata.wallman.ui.widgets.DailyAppWidget
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { false })
        }

        setResult(RESULT_CANCELED)
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
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
                    }
                }
            }
        }
    }

}