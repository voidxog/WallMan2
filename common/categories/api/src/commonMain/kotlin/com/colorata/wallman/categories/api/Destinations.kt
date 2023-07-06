package com.colorata.wallman.categories.api

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destinationSubPath
import kotlinx.collections.immutable.persistentListOf

fun Destinations.CategoriesDestination() = Destination("CategoriesList")
fun Destinations.CategoryDetailsDestination(category: WallpaperCategory? = null) = Destination(
    "Category/{id}",
    "Category/",
    arguments = persistentListOf(navArgument("id") { type = NavType.IntType })
).let { if (category != null) it + destinationSubPath(WallpaperCategory.values().indexOf(category)) else it }