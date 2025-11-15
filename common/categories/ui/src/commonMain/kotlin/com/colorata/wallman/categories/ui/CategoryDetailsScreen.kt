package com.voidxog.wallman2.categories.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voidxog.wallman2.categories.api.CategoryDetailsDestination
import com.voidxog.wallman2.categories.viewmodel.CategoryDetailsViewModel
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.continuousComposable
import com.voidxog.wallman2.core.data.parameter
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.ui.components.FilteredWallpaperCards

context(WallpapersModule)
fun MaterialNavGraphBuilder.categoryDetailsScreen() {
    continuousComposable(Destinations.CategoryDetailsDestination()) {
        val index by parameter()
        CategoryDetailsScreen(categoryIndex = index?.toInt() ?: 0)
    }
}

context(WallpapersModule)
@Composable
fun CategoryDetailsScreen(categoryIndex: Int, modifier: Modifier = Modifier) {
    val viewModel = viewModel { CategoryDetailsViewModel(categoryIndex) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryDetailsScreen(state, modifier)
}

@Composable
private fun CategoryDetailsScreen(
    state: CategoryDetailsViewModel.CategoryDetailsScreenState,
    modifier: Modifier = Modifier
) {
    FilteredWallpaperCards(
        onClick = {
            state.onEvent(CategoryDetailsViewModel.CategoryDetailsScreenEvent.GoToWallpaper(it))
        },
        rememberString(state.category.locale.name),
        modifier,
        description = rememberString(state.category.locale.description),
        wallpapers = state.wallpapers,
        onRandomWallpaper = {
            state.onEvent(CategoryDetailsViewModel.CategoryDetailsScreenEvent.GoToRandomWallpaper)
        }
    )
}
