package com.colorata.wallman.categories.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.wallman.categories.api.WallpaperCategory
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.module.NavigationController
import com.colorata.wallman.wallpapers.*
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.CategoryDetailsViewModel(index: Int) =
    CategoryDetailsViewModel(wallpapersRepository, navigationController, index)

class CategoryDetailsViewModel(
    repo: WallpapersRepository,
    private val navigation: NavigationController,
    index: Int
) : ViewModel() {
    private var category = WallpaperCategory.values()[index]

    private val wallpapers = category.categoryWallpapers(repo.wallpapers).toImmutableList()

    private fun goToWallpaper(wallpaper: WallpaperI) {
        navigation.navigate(Destinations.WallpaperDetailsDestination(wallpaper))
    }

    private fun goToRandomWallpaper() {
        navigation.navigate(Destinations.WallpaperDetailsDestination(wallpapers.random()))
    }

    val state by lazy {
        viewModelScope.launchMolecule(RecompositionClock.Immediate) {
            return@launchMolecule CategoryDetailsScreenState(
                wallpapers,
                category
            ) { event ->
                when (event) {
                    is CategoryDetailsScreenEvent.GoToWallpaper -> goToWallpaper(event.wallpaper)
                    is CategoryDetailsScreenEvent.GoToRandomWallpaper -> goToRandomWallpaper()
                }
            }
        }
    }

    data class CategoryDetailsScreenState(
        val wallpapers: ImmutableList<WallpaperI>,
        val category: WallpaperCategory,
        val onEvent: (CategoryDetailsScreenEvent) -> Unit
    )

    @Immutable
    sealed interface CategoryDetailsScreenEvent {
        object GoToRandomWallpaper : CategoryDetailsScreenEvent
        data class GoToWallpaper(val wallpaper: WallpaperI) : CategoryDetailsScreenEvent
    }
}