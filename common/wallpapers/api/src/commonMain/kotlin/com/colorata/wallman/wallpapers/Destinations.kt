package com.colorata.wallman.wallpapers

import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destinationArgument
import com.colorata.wallman.core.data.destinationSubPath
import kotlinx.collections.immutable.persistentListOf

fun Destinations.MainDestination() = Destination("Main")
fun Destinations.WallpaperDetailsDestination(wallpaper: WallpaperI? = null) = Destination(
    "Wallpaper/{hashCode}",
    "Wallpaper/",
    persistentListOf(destinationArgument("hashCode", "0"))
).let { if (wallpaper != null) it + destinationSubPath(wallpaper.hashCode()) else it }