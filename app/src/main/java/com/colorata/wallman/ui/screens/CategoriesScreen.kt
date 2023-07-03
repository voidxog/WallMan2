package com.colorata.wallman.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.asOther
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.arch.LocalPaddings
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.continuousComposable
import com.colorata.wallman.arch.flatComposable
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.presets.CategoryCard
import com.colorata.wallman.ui.presets.FilteredWallpaperCards
import com.colorata.wallman.ui.theme.animation
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.CategoriesViewModel
import com.colorata.wallman.viewmodels.CategoryDetailsViewModel

fun MaterialNavGraphBuilder.categoriesScreen() {
    flatComposable(CategoriesViewModel.CategoriesScreenDestination, hasContinuousChildren = true) {
        CategoriesScreen()
    }
}

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { CategoriesViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoriesScreen(state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
@Composable
private fun CategoriesScreen(
    state: CategoriesViewModel.CategoriesScreenState,
    modifier: Modifier = Modifier
) {
    val animatedList = remember { state.categories.toStaggerList({ 0f }, false) }
    LaunchedEffect(key1 = true) {
        animatedList.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }
    val animationSpec = fade(animationSpec = animation.emphasized()) + slideVertically(
        100f,
        animationSpec = animation.emphasized()
    )
    LazyColumn(modifier) {
        item {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.categories))
            })
        }
        itemsIndexed(animatedList) { index, it ->
            Column(
                modifier = Modifier.animateVisibility(
                    it.visible,
                    transition = animationSpec
                )
            ) {
                CategoryCard(
                    category = it.value, onClick = {
                        state.onEvent(
                            CategoriesViewModel.CategoriesScreenEvent.ClickOnCategory(
                                index
                            )
                        )
                    }, modifier = Modifier.padding(horizontal = MaterialTheme.spacing.large)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
        item {
            Spacer(modifier = Modifier.height(LocalPaddings.current.calculateBottomPadding()))
        }
    }
}

fun MaterialNavGraphBuilder.categoryDetailsScreen() {
    continuousComposable(CategoryDetailsViewModel.CategoryDetailsDestination) { entry ->
        val index =
            entry.arguments?.getInt(CategoryDetailsViewModel.CategoryDetailsDestination.arguments[0].name)
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