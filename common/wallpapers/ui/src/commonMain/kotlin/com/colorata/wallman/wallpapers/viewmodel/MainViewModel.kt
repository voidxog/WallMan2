package com.colorata.wallman.wallpapers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.module.NavigationController
import com.colorata.wallman.wallpapers.WallpaperDetailsDestination
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.WallpapersRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.MainViewModel() = MainViewModel(wallpapersRepository, navigationController)

class MainViewModel(
    private val repo: WallpapersRepository,
    private val navigation: NavigationController
) : ViewModel() {

    private fun goToRandomWallpaper() {
        navigation.navigate(
            Destinations.WallpaperDetailsDestination(repo.wallpapers.random())
        )
    }

    private fun onWallpaperClick(wallpaper: WallpaperI) =
        navigation.navigate(Destinations.WallpaperDetailsDestination(wallpaper))

    private val wallpapers = repo.wallpapers
    private val featuredWallpapers = wallpapers.takeLast(5).toImmutableList()
    val state by lazy {
        viewModelScope.launchMolecule(RecompositionClock.Immediate) {
            return@launchMolecule MainScreenState(
                wallpapers.toImmutableList(),
                featuredWallpapers
            ) {
                when (it) {
                    is MainScreenEvent.RandomWallpaper -> goToRandomWallpaper()
                    is MainScreenEvent.ClickOnWallpaper -> onWallpaperClick(it.wallpaper)
                }
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
        object RandomWallpaper : MainScreenEvent
        data class ClickOnWallpaper(val wallpaper: WallpaperI) : MainScreenEvent
    }
}
