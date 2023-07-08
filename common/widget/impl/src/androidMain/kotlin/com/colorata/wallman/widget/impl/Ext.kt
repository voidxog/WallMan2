package com.colorata.wallman.widget.impl

import android.app.Activity
import android.appwidget.AppWidgetManager
import androidx.activity.ComponentActivity
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.colorata.wallman.widget.api.EverydayWidget
import com.colorata.wallman.widget.ui_widget.EverydayWidgetContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun Activity.isWidgetConfiguration(): Boolean {
    setResult(ComponentActivity.RESULT_CANCELED)
    val appWidgetId = intent?.extras?.getInt(
        AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
    ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    return appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID
}

fun Activity.currentWidgetShapeId(): Flow<Int?> {
    return flow {
        emit(null)
        val manager = GlanceAppWidgetManager(this@currentWidgetShapeId)
        val glanceIds =
            manager.getGlanceIds(EverydayWidgetContent::class.java).last()
        updateAppWidgetState(this@currentWidgetShapeId, glanceIds) {
            it.apply {
                val id =
                    this[intPreferencesKey(EverydayWidget.shapeKey)]
                        ?: com.colorata.wallman.widget.api.R.drawable.ic_clever
                emit(id)
            }
        }
    }
}