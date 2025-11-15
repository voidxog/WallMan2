package com.voidxog.wallman2.wallpapers

import com.voidxog.wallman2.categories.api.WallpaperCategory

fun WallpaperCategory.categoryWallpapers(wallpapers: List<WallpaperI>) = wallpapers.filter { it.category == this }

