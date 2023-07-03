package com.colorata.wallman.wallpaper

import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.WallpaperCategoryLocale
import kotlinx.collections.immutable.ImmutableList

enum class WallpaperCategory(val locale: WallpaperCategoryLocale) {
    Appulse(Strings.Wallpapers.Categories.appulse),
    Wonders(Strings.Wallpapers.Categories.wonders),
    Peaceful(Strings.Wallpapers.Categories.peaceful),
    Fancy(Strings.Wallpapers.Categories.fancy),
    Garden(Strings.Wallpapers.Categories.garden),
    Birdies(Strings.Wallpapers.Categories.birdies)
}

fun WallpaperCategory.categoryWallpapers() = walls.filter { it.category == this }
