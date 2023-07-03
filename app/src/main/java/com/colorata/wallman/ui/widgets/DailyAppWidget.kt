package com.colorata.wallman.ui.widgets

import android.content.Context
import androidx.annotation.Keep
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.colorata.wallman.R
import com.colorata.wallman.arch.graph
import com.colorata.wallman.arch.widget.DailyDynamicWallpaperCallback
import com.colorata.wallman.arch.widget.WidgetUpdater
import com.colorata.wallman.arch.widget.rememberShapedImageBitmap
import com.colorata.wallman.wallpaper.DynamicWallpaper
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.supportsDynamicWallpapers
import java.util.concurrent.TimeUnit

@Keep
class DailyAppWidget : GlanceAppWidget() {
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    override val sizeMode: SizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        val size = LocalSize.current
        println(size)
        val squareSize = minOf(size.width, size.height)
        val state = kotlin.runCatching { currentState<Preferences>() }.getOrNull()
        val context = LocalContext.current
        val wallpapers = remember { context.graph.mainRepo.wallpapers }
        val shape = kotlin.runCatching { state?.get(intPreferencesKey(shapeKey.name)) }
            .getOrDefault(R.drawable.ic_clever) ?: R.drawable.ic_clever
        val hashCode =
            kotlin.runCatching { state?.get(intPreferencesKey(wallpaperHashcode.name)) }
                .getOrDefault(0)
        val firstWallpaper =
            wallpapers.first { it.supportsDynamicWallpapers() }.dynamicWallpapers[0]
        val currentWallpaper =
            if (hashCode == null) firstWallpaper
            else
                wallpapers.getDynamicWallpaperByHashCode(hashCode) ?: firstWallpaper

        Box(
            GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val shapedBitmap = rememberShapedImageBitmap(
                shape = shape,
                image = currentWallpaper.previewRes
            )
            Image(
                provider = ImageProvider(
                    shapedBitmap
                ),
                contentDescription = "",
                modifier = GlanceModifier.size(squareSize)
                    .clickable(actionRunCallback<DailyDynamicWallpaperCallback>())
                    .appWidgetBackground()
            )

            Box(
                GlanceModifier
                    .background(ImageProvider(R.drawable.appwidget_inner_background))
                    .cornerRadius(36.dp)
                    .clickable(actionRunCallback<DailyDynamicWallpaperCallback>())
                    .size(96.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    provider = ImageProvider(R.drawable.baseline_skip_next_24),
                    contentDescription = "",
                    modifier = GlanceModifier.size(24.dp)
                )
            }
        }
    }

    companion object {
        val shapeKey = ActionParameters.Key<Int>("shape")
        val wallpaper = ActionParameters.Key<Int>("wallpaper")
        val wallpaperHashcode = ActionParameters.Key<Int>("wallpaperHashcode")

        fun List<WallpaperI>.getDynamicWallpaperByHashCode(hashCode: Int): DynamicWallpaper? {
            val wallpaper =
                find { wallpaper -> wallpaper.dynamicWallpapers.any { it.hashCode() == hashCode } }
                    ?: return null

            return wallpaper.dynamicWallpapers.first { it.hashCode() == hashCode }
        }
    }
}

@Keep
class DailyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = DailyAppWidget()
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let {
            WorkManager.getInstance(it).enqueue(
                PeriodicWorkRequestBuilder<WidgetUpdater>(
                    24,
                    TimeUnit.HOURS,
                    10,
                    TimeUnit.MINUTES
                ).addTag("DailyWidget").build()
            )
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.let {
            WorkManager.getInstance(it).cancelAllWorkByTag("DailyWidget")
        }
    }
}