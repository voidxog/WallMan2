package com.colorata.wallman.settings.memory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.continuousComposable
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.modifiers.navigationBottomPadding
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.util.fullLineItem
import com.colorata.wallman.core.ui.util.rememberWindowSize
import com.colorata.wallman.settings.memory.api.MemoryDestination
import com.colorata.wallman.settings.memory.ui.components.CacheCard
import com.colorata.wallman.settings.memory.viewmodel.CacheViewModel
import com.colorata.wallman.wallpapers.WallpaperPacks
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.sizeInMb
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CacheScreen(state: CacheViewModel.CacheScreenState, modifier: Modifier = Modifier) {
    val ripple = remember {
        WallpaperPacks.entries.toPersistentList().mutate {
            it.removeAll { pack -> !pack.includesDynamic }
        }.toImmutableList()
    }
    val windowSize = rememberWindowSize()
    val bottomPadding = navigationBottomPadding()
    LaunchedEffect(Unit) {
        println(bottomPadding)
    }
    LazyVerticalStaggeredGrid(
        StaggeredGridCells.Fixed(if (windowSize.isCompact()) 1 else 2),
        modifier.padding(horizontal = MaterialTheme.spacing.large),
        verticalItemSpacing = MaterialTheme.spacing.large,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        contentPadding = PaddingValues(bottom = navigationBottomPadding())
    ) {
        fullLineItem {
            LargeTopAppBar(title = {
                Text(text = rememberString(Strings.memoryOptimization))
            })
        }
        items(ripple) { pack ->
            CacheCard(
                pack,
                rememberString(Strings.size, remember { pack.sizeInMb() }),
                Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
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