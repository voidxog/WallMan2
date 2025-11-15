package com.voidxog.wallman2.settings.memory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.continuousComposable
import com.voidxog.wallman2.core.data.mutate
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.core.ui.list.animatedAsGridAtLaunch
import com.voidxog.wallman2.core.ui.list.rememberVisibilityList
import com.voidxog.wallman2.core.ui.list.visibilityItems
import com.voidxog.wallman2.core.ui.modifiers.Padding
import com.voidxog.wallman2.core.ui.modifiers.navigationBarPadding
import com.voidxog.wallman2.core.ui.theme.screenPadding
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.core.ui.util.LocalWindowSizeConfiguration
import com.voidxog.wallman2.core.ui.util.fullLineItem
import com.voidxog.wallman2.settings.memory.api.MemoryDestination
import com.voidxog.wallman2.settings.memory.ui.components.CacheCard
import com.voidxog.wallman2.settings.memory.viewmodel.CacheViewModel
import com.voidxog.wallman2.wallpapers.WallpaperPacks
import com.voidxog.wallman2.wallpapers.WallpapersModule
import com.voidxog.wallman2.wallpapers.sizeInMb

context(WallpapersModule)
fun MaterialNavGraphBuilder.cacheScreen() {
    continuousComposable(Destinations.MemoryDestination()) {
        CacheScreen()
    }
}

context(WallpapersModule)
@Composable
fun CacheScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { CacheViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CacheScreen(state, modifier)
}

@Composable
private fun CacheScreen(state: CacheViewModel.CacheScreenState, modifier: Modifier = Modifier) {
    val windowSize = LocalWindowSizeConfiguration.current
    // TODO: refactor when https://gitlab.com/colorata/wallman/-/issues/1 fixed
    if (windowSize.isCompact()) {
        CacheScreenLayout(1, state, modifier)
    } else {
        CacheScreenLayout(2, state, modifier)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
private fun CacheScreenLayout(
    cellsCount: Int,
    state: CacheViewModel.CacheScreenState,
    modifier: Modifier = Modifier
) {
    val ripple = rememberVisibilityList { WallpaperPacks.entries.mutate {
        removeAll { pack -> !pack.includesDynamic }
    }}.animatedAsGridAtLaunch(cellsCount)

    LazyVerticalStaggeredGrid(
        StaggeredGridCells.Fixed(cellsCount),
        modifier.padding(
            horizontal = MaterialTheme.spacing.screenPadding
        ),
        verticalItemSpacing = MaterialTheme.spacing.large,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        contentPadding = PaddingValues(bottom = Padding.navigationBarPadding() + MaterialTheme.spacing.large)
    ) {
        fullLineItem {
            LargeTopAppBar(title = {
                Text(text = rememberString(Strings.memoryOptimization))
            })
        }
        visibilityItems(ripple) { pack ->
            CacheCard(
                pack,
                rememberString(Strings.size, remember { pack.sizeInMb() }),
                isCacheEnabled = remember(state) {
                    state.downloadedWallpaperPacks.any { downloaded -> downloaded == pack }
                },
                isDeleteEnabled = remember(state) {
                    state.installedWallpaperPacks.any { installed -> installed == pack }
                },
                onClearCache = {
                    state.onEvent(CacheViewModel.CacheScreenEvent.ClearCache(pack))
                },
                onDelete = {
                    state.onEvent(CacheViewModel.CacheScreenEvent.DeletePack(pack))
                },
            )
        }
    }
}

