package com.voidxog.wallman2.wallpapers

import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.destination
import com.voidxog.wallman2.core.data.withArgument

fun Destinations.MainDestination() = destination("Main")
fun Destinations.WallpaperDetailsDestination(wallpaperIndex: Int? = null) = destination(
    "Wallpaper/{index}", "Wallpaper/"
).withArgument(
    wallpaperIndex, argumentName = "index", defaultValue = ""
) { it }
