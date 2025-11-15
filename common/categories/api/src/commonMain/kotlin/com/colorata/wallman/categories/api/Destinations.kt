package com.voidxog.wallman2.categories.api

import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.destination
import com.voidxog.wallman2.core.data.withArgument

fun Destinations.CategoriesDestination() = destination("CategoriesList")
fun Destinations.CategoryDetailsDestination(category: WallpaperCategory? = null) = destination(
    "Category/{index}", "Category/"
).withArgument(
    category, argumentName = "index", defaultValue = "0"
) {
    WallpaperCategory.entries.indexOf(it)
}
