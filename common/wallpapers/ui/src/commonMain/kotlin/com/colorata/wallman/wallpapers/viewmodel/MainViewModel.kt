package com.voidxog.wallman2.wallpapers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.lazyMolecule
import com.voidxog.wallman2.core.data.module.NavigationController
import com.voidxog.wallman2.wallpapers.WallpaperDetailsDestination
import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.WallpapersRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.MainViewModel() =
    MainViewModel(wallpapersRepository, navigationController)

class MainViewModel(
    private val repo: WallpapersRepository,
    private val navigation: NavigationController
) : ViewModel() {

    private fun goToRandomWallpaper() {
        navigation.navigate(
            Destinations.WallpaperDetailsDestination(repo.wallpapers.indices.random())
        )
    }

    private fun onWallpaperClick(wallpaperIndex: Int) =
        navigation.navigate(Destinations.WallpaperDetailsDestination(wallpaperIndex))

    private val wallpapers = repo.wallpapers
    private val featuredWallpapers = wallpapers.takeLast(15).toImmutableList()

    val state by lazyMolecule {
        MainScreenState(
            wallpapers.toImmutableList(),
            featuredWallpapers
        ) {
            when (it) {
                is MainScreenEvent.RandomWallpaper -> goToRandomWallpaper()
                is MainScreenEvent.ClickOnWallpaper -> onWallpaperClick(repo.wallpapers.indexOf(it.wallpaper))
            }
        }
    }

    data class MainScreenState(
        val wallpapers: ImmutableList<WallpaperI>,
        val featuredWallpapers: ImmutableList<WallpaperI>,
        val onEvent: (MainScreenEvent) -> Unit
    )


    @Immutable
    sealed interface MainScreenEvent {
        data object RandomWallpaper : MainScreenEvent
        data class ClickOnWallpaper(val wallpaper: WallpaperI) : MainScreenEvent
    }
}

