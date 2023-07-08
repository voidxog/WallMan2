package com.colorata.wallman.widget.impl

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.Configuration
import androidx.work.WorkManager
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.widget.api.EverydayWidget
import com.colorata.wallman.widget.api.EverydayWidgetRepository
import com.colorata.wallman.widget.api.R
import com.colorata.wallman.widget.api.ShapeConfiguration
import com.colorata.wallman.widget.ui_widget.EverydayWidgetContent

class EverydayWidgetRepositoryImpl(
    private val currentShapeId: Int?, private val context: Context
) : EverydayWidgetRepository {
    override val shapes = listOf(
        ShapeConfiguration(
            R.drawable.ic_clever,
            Strings.clever
        ),
        ShapeConfiguration(
            R.drawable.ic_flower,
            Strings.flower
        ),
        ShapeConfiguration(
            R.drawable.ic_scallop,
            Strings.scallop
        ),
        ShapeConfiguration(
            R.drawable.ic_circle,
            Strings.circle
        ),
        ShapeConfiguration(
            R.drawable.ic_square,
            Strings.square
        )
    )

    override fun currentShape(): ShapeConfiguration? {
        return shapes.find { it.resId == currentShapeId }
    }

    override suspend fun updateShape(configuration: ShapeConfiguration) {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds =
            manager.getGlanceIds(EverydayWidgetContent::class.java).last()
        updateAppWidgetState(context, glanceIds) {
            it.apply {
                this[intPreferencesKey(EverydayWidget.shapeKey)] =
                    configuration.resId
            }
        }
        createEverydayWidgetContent().update(context, glanceIds)
    }

    override fun initializeWorkManager() {
        WorkManager.initialize(context, Configuration.Builder().build())
    }
}