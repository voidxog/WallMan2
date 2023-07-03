package com.colorata.wallman.viewmodels

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.repos.MainRepo
import com.colorata.wallman.repos.randomWallpaper
import com.colorata.wallman.wallpaper.WallpaperI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun Graph.MainViewModel() = MainViewModel(mainRepo)

@OptIn(ExperimentalStaggerApi::class)
class MainViewModel(
    private val repo: MainRepo
) : ViewModel() {

    var list by mutableStateOf(repo.wallpapers.map { it }.toStaggerList({ 0f }, false))
    private fun goToRandomWallpaper() {
        repo.goToWallpaper(repo.randomWallpaper())
    }

    private fun onWallpaperClick(wallpaper: WallpaperI) =
        repo.goToWallpaper(wallpaper)

    private val wallpapers = repo.wallpapers
    private val featuredWallpapers = wallpapers.takeLast(5).toImmutableList()
    val state by lazy {
        viewModelScope.launchMolecule(RecompositionClock.Immediate) {
            return@launchMolecule MainScreenState(
                wallpapers,
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

    companion object {
        val MainScreenDestination = Destination("Main")
    }
}
