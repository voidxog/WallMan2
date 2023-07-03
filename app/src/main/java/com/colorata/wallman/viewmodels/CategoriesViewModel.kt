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
import com.colorata.wallman.wallpaper.WallpaperCategory
import com.colorata.wallman.wallpaper.categoryWallpapers
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun Graph.CategoriesViewModel() = CategoriesViewModel(mainRepo)

@OptIn(ExperimentalStaggerApi::class)
class CategoriesViewModel(
    private val repo: MainRepo
) : ViewModel() {
    val categories: ImmutableList<WallpaperCategory>
        get() = WallpaperCategory.values().toList().toImmutableList()

    var wallpapers by mutableStateOf(categories[0].categoryWallpapers().map { it }.toStaggerList({ 0f }, false))

    fun onClickCategoryCard(index: Int) {
        repo.navController.navigate("Category/$index") {
            launchSingleTop = true
        }
    }

    val state by lazy {
        viewModelScope.launchMolecule(RecompositionClock.Immediate) {
            return@launchMolecule CategoriesScreenState(
                categories
            ) { event ->
                when (event) {
                    is CategoriesScreenEvent.ClickOnCategory -> onClickCategoryCard(event.index)
                }
            }
        }
    }

    data class CategoriesScreenState(
        val categories: ImmutableList<WallpaperCategory>,
        val onEvent: (CategoriesScreenEvent) -> Unit
    )
    @Immutable
    sealed interface CategoriesScreenEvent {
        data class ClickOnCategory(val index: Int): CategoriesScreenEvent
    }

    companion object {
        val CategoriesScreenDestination = Destination("CategoriesList")
    }
}