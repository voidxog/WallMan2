package com.colorata.wallman.repos

import android.app.Application
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.colorata.wallman.arch.Badge
import com.colorata.wallman.wallpaper.WallpaperI
import kotlinx.collections.immutable.ImmutableList

interface MainRepo {
    val wallpapers: ImmutableList<WallpaperI>
    val navController: NavHostController
    fun goToWallpaper(wallpaper: WallpaperI)
}

fun MainRepo.randomWallpaper() = wallpapers.random()