package com.voidxog.wallman2.categories.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.voidxog.wallman2.categories.api.CategoryDetailsDestination
import com.voidxog.wallman2.categories.api.WallpaperCategory
import com.voidxog.wallman2.core.data.module.NavigationController
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.lazyMolecule
import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.WallpapersRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WallpapersModule.CategoriesViewModel() =
    CategoriesViewModel(wallpapersRepository, navigationController)

class CategoriesViewModel(
    private val repo: WallpapersRepository,
    private val navigation: NavigationController
) : ViewModel() {
    private val categories: ImmutableList<WallpaperCategory>
        get() = WallpaperCategory.entries.toImmutableList()

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
