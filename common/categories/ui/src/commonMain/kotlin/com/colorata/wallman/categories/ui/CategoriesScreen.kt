package com.colorata.wallman.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.animateaslifestyle.stagger.Element
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.categories.api.CategoriesDestination
import com.colorata.wallman.categories.api.WallpaperCategory
import com.colorata.wallman.categories.ui.components.generateShapesForCard
import com.colorata.wallman.categories.viewmodel.CategoriesViewModel
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.modifiers.Padding
import com.colorata.wallman.core.ui.modifiers.navigationBarPadding
import com.colorata.wallman.core.ui.modifiers.navigationPadding
import com.colorata.wallman.core.ui.theme.emphasizedVerticalSlide
import com.colorata.wallman.core.ui.theme.screenPadding
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration
import com.colorata.wallman.core.ui.util.fullLineItem
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.categoryWallpapers
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
    if (windowSize.isCompact()) {
        CategoriesLayout(
            StaggeredGridCells.Fixed(1),
            state,
            modifier
        )
    } else {
        CategoriesLayout(
            StaggeredGridCells.Fixed(2),
            state,
            modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
private fun CategoriesLayout(
    cells: StaggeredGridCells,
    state: CategoriesViewModel.CategoriesScreenState,
    modifier: Modifier = Modifier
) {
    val animatedList = remember { state.categories.toStaggerList({ 0f }, false) }
    LaunchedEffect(key1 = true) {
        animatedList.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }

    val elementsSpacing = MaterialTheme.spacing.medium
    val horizontalPadding = MaterialTheme.spacing.screenPadding

    val shapes = MaterialTheme.shapes
    val generatedShapes = remember {
        List(state.categories.size) { generateShapesForCard(shapes) }.toImmutableList()
    }
    LazyVerticalStaggeredGrid(
        cells,
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
        itemsIndexed(animatedList) { index, it ->
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
    category: Element<WallpaperCategory, Float>,
    onClick: () -> Unit,
    wallpapers: ImmutableList<WallpaperI>,
    shapes: ImmutableList<Shape>,
    modifier: Modifier = Modifier
) {
    com.colorata.wallman.categories.ui.components.CategoryCard(
        category = category.value, wallpapers = remember(category.value) {
            category.value.categoryWallpapers(wallpapers).toImmutableList()
        },
        shapes = shapes,
        onClick = {
            onClick()
        },
        modifier = modifier.animateVisibility(
            category.visible,
            transition = MaterialTheme.animation.emphasizedVerticalSlide()
        )
    )
}

