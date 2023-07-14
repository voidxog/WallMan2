package com.colorata.wallman.wallpapers

import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destination
import com.colorata.wallman.core.data.withArgument

fun Destinations.MainDestination() = destination("Main")
fun Destinations.WallpaperDetailsDestination(wallpaper: WallpaperI? = null) = destination(
    "Wallpaper/{hashCode}", "Wallpaper/"
).withArgument(
    wallpaper, argumentName = "hashCode", defaultValue = "0"
) { it.hashCode() }