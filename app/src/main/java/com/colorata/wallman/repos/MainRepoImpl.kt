package com.colorata.wallman.repos

import android.app.Application
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.colorata.wallman.arch.Badge
import com.colorata.wallman.vibrate
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.walls
import kotlinx.collections.immutable.ImmutableList

class MainRepoImpl(
    private val application: Application,
    override val navController: NavHostController
) : MainRepo {
    override val wallpapers: ImmutableList<WallpaperI> = walls

    override fun goToWallpaper(
        wallpaper: WallpaperI
    ) {
        application.vibrate()
        navController.navigate("Wallpaper/${wallpaper.hashCode()}")
    }
}