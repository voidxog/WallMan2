package com.colorata.wallman.ui.screens.settings

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.material3.groupedItems
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.continuousComposable
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.presets.CacheCard
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.settings.CacheViewModel
import com.colorata.wallman.wallpaper.WallpaperPacks
import com.colorata.wallman.wallpaper.sizeInMb
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

fun MaterialNavGraphBuilder.cacheScreen() {
    continuousComposable(CacheViewModel.CacheScreenDestination) {
        CacheScreen()
    }
}
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
        WallpaperPacks.values().toList().toPersistentList().mutate {
            it.removeAll { pack -> !pack.includesDynamic }
        }.toImmutableList()
    }
    LazyColumn(modifier.padding(horizontal = MaterialTheme.spacing.large)) {
        item {
            LargeTopAppBar(title = {
                Text(text = rememberString(Strings.memoryOptimization))
            })
        }
        groupedItems(ripple, Orientation.Vertical, padding = 5.dp, outerCorner = 20.dp) { pack ->
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
        item {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(MaterialTheme.spacing.medium)
            )
        }
    }
}