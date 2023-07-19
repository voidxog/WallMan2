package com.colorata.wallman.categories.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.colorata.wallman.categories.api.CategoryDetailsDestination
import com.colorata.wallman.categories.api.WallpaperCategory
import com.colorata.wallman.core.data.module.NavigationController
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.lazyMolecule
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.WallpapersRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.CategoriesViewModel() =
    CategoriesViewModel(wallpapersRepository, navigationController)

class CategoriesViewModel(
    private val repo: WallpapersRepository,
    private val navigation: NavigationController
) : ViewModel() {
    private val categories: ImmutableList<WallpaperCategory>
        get() = WallpaperCategory.values().toList().toImmutableList()

    private fun onClickCategoryCard(index: Int) {
        navigation.navigate(Destinations.CategoryDetailsDestination(categories[index]))
    }

    val state by lazyMolecule {
        CategoriesScreenState(
            categories,
            repo.wallpapers.toImmutableList()
        ) { event ->
            when (event) {
                is CategoriesScreenEvent.ClickOnCategory -> onClickCategoryCard(event.index)
            }
        }
    }

    data class CategoriesScreenState(
        val categories: ImmutableList<WallpaperCategory>,
        val wallpapers: ImmutableList<WallpaperI>,
        val onEvent: (CategoriesScreenEvent) -> Unit
    )

    @Immutable
    sealed interface CategoriesScreenEvent {
        data class ClickOnCategory(val index: Int) : CategoriesScreenEvent
    }
}