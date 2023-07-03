package com.colorata.wallman.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.repos.MainRepo
import com.colorata.wallman.wallpaper.WallpaperCategory
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.categoryWallpapers
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

fun Graph.CategoryDetailsViewModel(index: Int) = CategoryDetailsViewModel(mainRepo, index)

class CategoryDetailsViewModel(
    private val repo: MainRepo,
    index: Int
) : ViewModel() {
    private var category = WallpaperCategory.values()[index]

    private val wallpapers = category.categoryWallpapers().toImmutableList()

    private fun goToWallpaper(wallpaper: WallpaperI) {
        repo.goToWallpaper(wallpaper)
    }

    private fun goToRandomWallpaper() {
        repo.goToWallpaper(wallpapers.random())
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

    companion object {
        val CategoryDetailsDestination = Destination(
            "Category/{id}",
            persistentListOf(navArgument("id") { type = NavType.IntType })
        )
    }
}