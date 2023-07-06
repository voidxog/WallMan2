package com.colorata.wallman.categories.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.categories.api.CategoryDetailsDestination
import com.colorata.wallman.categories.viewmodel.CategoryDetailsViewModel
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.continuousComposable
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.viewModel
import com.colorata.wallman.wallpapers.ui.components.FilteredWallpaperCards

fun MaterialNavGraphBuilder.categoryDetailsScreen() {
    continuousComposable(Destinations.CategoryDetailsDestination()) { entry ->
        val index =
            entry.arguments?.getInt(Destinations.CategoryDetailsDestination().arguments[0].name)
                ?: 0
        CategoryDetailsScreen(categoryIndex = index)
    }
}

@Composable
fun CategoryDetailsScreen(categoryIndex: Int, modifier: Modifier = Modifier) {
    val viewModel = viewModel { CategoryDetailsViewModel(categoryIndex) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryDetailsScreen(state, modifier)
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
private fun CategoryDetailsScreen(
    state: CategoryDetailsViewModel.CategoryDetailsScreenState,
    modifier: Modifier = Modifier
) {
    val animatedWallpapers =
        remember(state.wallpapers) {
            state.wallpapers.toStaggerList(
                { 0f },
                visible = false
            )
        }

    FilteredWallpaperCards(
        onClick = {
            state.onEvent(CategoryDetailsViewModel.CategoryDetailsScreenEvent.GoToWallpaper(it))
        },
        rememberString(state.category.locale.name),
        modifier,
        description = rememberString(state.category.locale.description),
        wallpapers = animatedWallpapers,
        onRandomWallpaper = {
            state.onEvent(CategoryDetailsViewModel.CategoryDetailsScreenEvent.GoToRandomWallpaper)
        },
        withNavbar = false
    )
}