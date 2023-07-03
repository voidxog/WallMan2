package com.colorata.wallman.arch.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.colorata.wallman.ui.widgets.DailyAppWidget

class WidgetUpdater(private val context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        manager.getGlanceIds(DailyAppWidget::class.java).forEach {
            updateAppWidgetState(context, it) { pref ->
                pref.updateWallpaper(context)
            }
            DailyAppWidget().update(context, it)
        }
        return Result.success()
    }
}