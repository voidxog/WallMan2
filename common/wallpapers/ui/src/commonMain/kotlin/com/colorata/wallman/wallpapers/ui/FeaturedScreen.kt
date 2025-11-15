package com.voidxog.wallman2.wallpapers.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.material3.isCompact
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.bitmapAsset
import com.voidxog.wallman2.core.data.flatComposable
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.core.ui.util.LocalWindowSizeConfiguration
import com.voidxog.wallman2.wallpapers.MainDestination
import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.firstPreviewRes
import com.voidxog.wallman2.wallpapers.ui.components.FeaturedWallpapersCarousel
import com.voidxog.wallman2.wallpapers.ui.components.FilteredWallpaperCards
import com.voidxog.wallman2.wallpapers.viewmodel.MainViewModel
import kotlinx.collections.immutable.toImmutableList

context(WallpapersModule)
fun MaterialNavGraphBuilder.mainScreen() {
    flatComposable(Destinations.MainDestination()) {
        FeaturedScreen()
    }
}

context(WallpapersModule)
@Composable
fun FeaturedScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    FeaturedScreen(state, modifier)
}

@Composable
private fun FeaturedScreen(state: MainViewModel.MainScreenState, modifier: Modifier = Modifier) {
    var selectedWallpaper by remember { mutableStateOf<WallpaperI?>(null) }
    var currentImageAsset by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(selectedWallpaper) {
        currentImageAsset = selectedWallpaper?.firstPreviewRes()
    }

    val windowSize = LocalWindowSizeConfiguration.current
    val featuredWallpapers = remember(windowSize, state.featuredWallpapers) {
        state.featuredWallpapers.takeLast(if (windowSize.isCompact()) 5 else 10).toImmutableList()
    }
    FilteredWallpaperCards(
        onClick = {
            state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
        },
        name = rememberString(Strings.exploreNew),
        modifier = modifier,
        startItem = {
            FeaturedWallpapersCarousel(
                featuredWallpapers,
                onClick = {
                    state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
                },
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.extraLarge),
                onWallpaperRotation = {
                    selectedWallpaper = it
                })
        },
        wallpapers = state.wallpapers,
        onRandomWallpaper = {
            state.onEvent(MainViewModel.MainScreenEvent.RandomWallpaper)
        },
        backgroundImageBitmap = currentImageAsset?.let { bitmapAsset(it) },
        applyNavigationPadding = true
    )
}
