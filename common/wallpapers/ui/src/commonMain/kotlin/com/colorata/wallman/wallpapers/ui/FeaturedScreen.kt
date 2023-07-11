package com.colorata.wallman.wallpapers.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.wallpapers.MainDestination
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.ui.components.FeaturedWallpapersCarousel
import com.colorata.wallman.wallpapers.ui.components.FilteredWallpaperCards
import com.colorata.wallman.wallpapers.viewmodel.MainViewModel

context(WallpapersModule)
fun MaterialNavGraphBuilder.mainScreen() {
    flatComposable(Destinations.MainDestination()) { FeaturedScreen() }
}

context(WallpapersModule)
@Composable
fun FeaturedScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    FeaturedScreen(state, modifier)
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
private fun FeaturedScreen(state: MainViewModel.MainScreenState, modifier: Modifier = Modifier) {
    FilteredWallpaperCards(onClick = {
        state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
    }, wallpapers = remember(state.wallpapers) {
        state.wallpapers.toStaggerList(
            { 0f }, visible = false
        )
    }, startItem = {
        FeaturedWallpapersCarousel(state.featuredWallpapers, onClick = {
            state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
        }, Modifier.padding(vertical = MaterialTheme.spacing.extraLarge))
    }, name = rememberString(Strings.exploreNew), onRandomWallpaper = {
        state.onEvent(MainViewModel.MainScreenEvent.RandomWallpaper)
    }, modifier = modifier
    )
}