package com.colorata.wallman.wallpapers

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destinationSubPath
import kotlinx.collections.immutable.persistentListOf

fun Destinations.MainDestination() = Destination("Main")
fun Destinations.WallpaperDetailsDestination(wallpaper: WallpaperI? = null) = Destination(
    "Wallpaper/{id}",
    "Wallpaper/",
    persistentListOf(navArgument("id") { type = NavType.IntType })
).let { if (wallpaper != null) it + destinationSubPath(wallpaper.hashCode()) else it }