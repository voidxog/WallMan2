package com.colorata.wallman.categories.api

import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.destination
import com.colorata.wallman.core.data.withArgument

fun Destinations.CategoriesDestination() = destination("CategoriesList")
fun Destinations.CategoryDetailsDestination(category: WallpaperCategory? = null) = destination(
    "Category/{index}", "Category/"
).withArgument(
    category, argumentName = "index", defaultValue = "0"
) {
    WallpaperCategory.entries.indexOf(it)
}