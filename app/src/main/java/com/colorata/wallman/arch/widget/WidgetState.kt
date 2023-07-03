package com.colorata.wallman.arch.widget

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.GlanceStateDefinition
import com.colorata.wallman.ui.widgets.DailyAppWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class WidgetState(
    val context: Context,
    val preferencesGlanceStateDefinition: GlanceStateDefinition<Preferences>,
    val glanceId: GlanceId
) {
    fun update(
        content: MutablePreferences.() -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                updateAppWidgetState(
                    context = context,
                    preferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) {
                    it.toMutablePreferences().apply {
                        content()
                    }
                }
                DailyAppWidget().update(context = context, glanceId = glanceId)
            }
        }
    }
}