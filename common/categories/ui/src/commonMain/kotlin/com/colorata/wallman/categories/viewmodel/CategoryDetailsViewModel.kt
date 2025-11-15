package com.voidxog.wallman2.categories.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.voidxog.wallman2.categories.api.WallpaperCategory
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.lazyMolecule
import com.voidxog.wallman2.core.data.module.NavigationController
import com.voidxog.wallman2.wallpapers.*
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.CategoryDetailsViewModel(index: Int) =
    CategoryDetailsViewModel(wallpapersRepository, navigationController, index)

class CategoryDetailsViewModel(
    private val repo: WallpapersRepository,
    private val navigation: NavigationController,
    index: Int
) : ViewModel() {
    private var category = WallpaperCategory.entries[index]

    private val wallpapers = category.categoryWallpapers(repo.wallpapers).toImmutableList()

    private fun goToWallpaper(index: Int) {
        navigation.navigate(Destinations.WallpaperDetailsDestination(index))
    }

    private fun goToRandomWallpaper() {
        navigation.navigate(
            Destinations.WallpaperDetailsDestination(
                repo.wallpapers.indexOf(
                    wallpapers.random()
                )
            )
        )
    }

    val state by lazyMolecule {
        CategoryDetailsScreenState(
            wallpapers,
            category
        ) { event ->
            when (event) {
                is CategoryDetailsScreenEvent.GoToWallpaper -> goToWallpaper(
                    repo.wallpapers.indexOf(
                        event.wallpaper
                    )
                )

                is CategoryDetailsScreenEvent.GoToRandomWallpaper -> goToRandomWallpaper()
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
        data object GoToRandomWallpaper : CategoryDetailsScreenEvent
        data class GoToWallpaper(val wallpaper: WallpaperI) : CategoryDetailsScreenEvent
    }
}
