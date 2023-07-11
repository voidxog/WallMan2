package com.colorata.wallman.categories.api

import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destinationArgument
import com.colorata.wallman.core.data.destinationSubPath
import kotlinx.collections.immutable.persistentListOf

fun Destinations.CategoriesDestination() = Destination("CategoriesList")
fun Destinations.CategoryDetailsDestination(category: WallpaperCategory? = null) = Destination(
    "Category/{index}",
    "Category/",
    arguments = persistentListOf(
        destinationArgument(
            "index",
            "0"
        )
    )
).let {
    if (category != null) it + destinationSubPath(
        WallpaperCategory.values().indexOf(category)
    ) else it
}