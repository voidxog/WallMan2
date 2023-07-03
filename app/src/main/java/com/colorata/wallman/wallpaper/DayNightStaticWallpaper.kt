package com.colorata.wallman.wallpaper

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class DayNightStaticWallpaper(
    val day: String,
    @DrawableRes val dayPreview: Int,
    val night: String,
    @DrawableRes val nightPreview: Int
)

fun dayNightWallpaperOf(
    day: String,
    @DrawableRes dayPreview: Int,
    night: String = day,
    @DrawableRes nightPreview: Int = dayPreview
): DayNightStaticWallpaper =
    DayNightStaticWallpaper(day, dayPreview, night, nightPreview)