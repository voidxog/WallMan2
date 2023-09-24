package com.colorata.wallman.widget.ui_widget

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.colorata.wallman.core.di.graph
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import com.colorata.wallman.widget.api.EverydayWidget
import com.colorata.wallman.widget.api.R

class EverydayWidgetContent(
    private val onClick: Action
) : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val wallpapers = context.graph.wallpapersModule.wallpapersRepository.wallpapers
        provideContent {
            val size = LocalSize.current
            val squareSize = minOf(size.width, size.height)
            val state = runCatching { currentState<Preferences>() }.getOrNull()
            val shape = runCatching { state?.get(intPreferencesKey(EverydayWidget.shapeKey)) }
                .getOrDefault(R.drawable.ic_clever) ?: R.drawable.ic_clever
            val hashCode =
                runCatching { state?.get(intPreferencesKey(EverydayWidget.wallpaperHashcode)) }
                    .getOrDefault(0)
            val firstWallpaper =
                wallpapers.first { it.supportsDynamicWallpapers() }.dynamicWallpapers[0]
            val currentWallpaper =
                if (hashCode == null) firstWallpaper
                else
                    EverydayWidget.run {
                        wallpapers.getDynamicWallpaperByHashCode(hashCode) ?: firstWallpaper
                    }

            println(currentWallpaper)
            Box(
                GlanceModifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val bitmap = remember(currentWallpaper.previewRes) {
                    val fullPath =
                        context.assets.list("")
                            ?.first { currentWallpaper.previewRes in it.split(".", "/") }
                            ?: error("Asset not found")

                    context.assets.open(fullPath)
                        .use { BitmapFactory.decodeStream(it) }
                        .asImageBitmap()
                }
                val shapedBitmap = rememberShapedImageBitmap(
                    shape = shape,
                    image = bitmap.asAndroidBitmap()
                )
                Image(
                    provider = ImageProvider(
                        shapedBitmap
                    ),
                    contentDescription = "",
                    modifier = GlanceModifier.size(squareSize)
                        .clickable(onClick)
                        .appWidgetBackground()
                )

                Box(
                    GlanceModifier
                        .background(ImageProvider(R.drawable.appwidget_inner_background))
                        .cornerRadius(36.dp)
                        .clickable(onClick)
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
    }
}