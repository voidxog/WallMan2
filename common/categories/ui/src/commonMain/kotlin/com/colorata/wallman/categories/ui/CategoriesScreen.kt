package com.voidxog.wallman2.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.material3.isCompact
import com.voidxog.wallman2.categories.api.CategoriesDestination
import com.voidxog.wallman2.categories.api.WallpaperCategory
import com.voidxog.wallman2.categories.ui.components.generateShapesForCard
import com.voidxog.wallman2.categories.viewmodel.CategoriesViewModel
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.flatComposable
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.core.ui.list.animatedAsGridAtLaunch
import com.voidxog.wallman2.core.ui.list.rememberVisibilityList
import com.voidxog.wallman2.core.ui.list.visibilityItemsIndexed
import com.voidxog.wallman2.core.ui.modifiers.Padding
import com.voidxog.wallman2.core.ui.modifiers.navigationBarPadding
import com.voidxog.wallman2.core.ui.modifiers.navigationPadding
import com.voidxog.wallman2.core.ui.theme.screenPadding
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.core.ui.util.LocalWindowSizeConfiguration
import com.voidxog.wallman2.core.ui.util.fullLineItem
import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.categoryWallpapers
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

context(WallpapersModule)
fun MaterialNavGraphBuilder.categoriesScreen() {
    flatComposable(Destinations.CategoriesDestination(), hasContinuousChildren = true) {
        CategoriesScreen(Modifier.navigationPadding())
    }
}

context(WallpapersModule)
@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { CategoriesViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoriesScreen(state, modifier)
}

@Composable
private fun CategoriesScreen(
    state: CategoriesViewModel.CategoriesScreenState, modifier: Modifier = Modifier
) {
    // TODO: refactor when https://gitlab.com/colorata/wallman/-/issues/1 fixed
    val windowSize = LocalWindowSizeConfiguration.current
    CategoriesLayout(
        if (windowSize.isCompact()) 1 else 2,
        state,
        modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoriesLayout(
    cellsCount: Int,
    state: CategoriesViewModel.CategoriesScreenState,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyStaggeredGridState()
    val animatedList = rememberVisibilityList {
        state.categories
    }.animatedAsGridAtLaunch(cellsCount, gridState, indexOffset = 1)

    val elementsSpacing = MaterialTheme.spacing.medium
    val horizontalPadding = MaterialTheme.spacing.screenPadding

    val shapes = MaterialTheme.shapes
    val generatedShapes = remember {
        List(state.categories.size) { generateShapesForCard(shapes) }.toImmutableList()
    }
    LazyVerticalStaggeredGrid(
        StaggeredGridCells.Fixed(cellsCount),
        state = gridState,
        modifier = modifier.fillMaxSize(),
        verticalItemSpacing = elementsSpacing,
        horizontalArrangement = Arrangement.spacedBy(elementsSpacing),
        contentPadding = PaddingValues(
            start = horizontalPadding,
            end = horizontalPadding,
            bottom = Padding.navigationBarPadding() + horizontalPadding
        )
    ) {
        fullLineItem {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.categories))
            })
        }
        visibilityItemsIndexed(animatedList) { index, it ->
            CategoryCard(
                category = it, onClick = {
                    state.onEvent(
                        CategoriesViewModel.CategoriesScreenEvent.ClickOnCategory(
                            index
                        )
                    )
                }, wallpapers = state.wallpapers,
                generatedShapes[index]
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: WallpaperCategory,
    onClick: () -> Unit,
    wallpapers: ImmutableList<WallpaperI>,
    shapes: ImmutableList<Shape>,
    modifier: Modifier = Modifier
) {
    com.voidxog.wallman2.categories.ui.components.CategoryCard(
        category = category, wallpapers = remember(category) {
            category.categoryWallpapers(wallpapers).toImmutableList()
        },
        shapes = shapes,
        onClick = {
            onClick()
        },
        modifier
    )
}


