package com.colorata.wallman.categories.ui

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
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.categories.api.CategoriesDestination
import com.colorata.wallman.categories.ui.components.CategoryCard
import com.colorata.wallman.categories.viewmodel.CategoriesViewModel
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.modifiers.navigationPadding
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.theme.LocalPaddings
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.categoryWallpapers
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
    val animationSpec =
        fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
            100f,
            animationSpec = MaterialTheme.animation.emphasized()
        )
    LazyColumn(
        modifier
    ) {
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
                    category = it.value,
                    wallpapers = remember(it.value) {
                        it.value.categoryWallpapers(state.wallpapers).toImmutableList()
                    },
                    onClick = {
                        state.onEvent(
                            CategoriesViewModel.CategoriesScreenEvent.ClickOnCategory(
                                index
                            )
                        )
                    },
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.large)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
        item {
            Spacer(modifier = Modifier.height(LocalPaddings.current.calculateBottomPadding()))
        }
    }
}