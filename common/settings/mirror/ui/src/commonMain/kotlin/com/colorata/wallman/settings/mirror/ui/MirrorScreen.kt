package com.voidxog.wallman2.settings.mirror.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.colorata.animateaslifestyle.material3.isCompact
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.continuousComposable
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.core.ui.list.animatedAsGridAtLaunch
import com.voidxog.wallman2.core.ui.list.rememberVisibilityList
import com.voidxog.wallman2.core.ui.list.visibilityItems
import com.voidxog.wallman2.core.ui.theme.screenPadding
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.core.ui.util.LocalWindowSizeConfiguration
import com.voidxog.wallman2.core.ui.util.fullLineItem
import com.voidxog.wallman2.settings.mirror.api.MirrorDestination
import com.voidxog.wallman2.settings.mirror.ui.components.MirrorCard
import com.voidxog.wallman2.settings.mirror.viewmodel.MirrorViewModel

context(CoreModule)
fun MaterialNavGraphBuilder.mirrorScreen() {
    continuousComposable(Destinations.MirrorDestination()) {
        MirrorScreen()
    }
}

context(CoreModule)
@Composable
fun MirrorScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MirrorViewModel() }
    val state by viewModel.state.collectAsState()
    MirrorScreen(state, modifier)
}

@Composable
fun MirrorScreen(state: MirrorViewModel.MirrorScreenState, modifier: Modifier = Modifier) {
    val windowSize = LocalWindowSizeConfiguration.current
    if (windowSize.isCompact()) {
        MirrorScreenLayout(
            cellsCount = 1, state, modifier
        )
    } else {
        MirrorScreenLayout(
            cellsCount = 2, state, modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MirrorScreenLayout(
    cellsCount: Int,
    state: MirrorViewModel.MirrorScreenState,
    modifier: Modifier,
) {
    val animatedMirrors =
        rememberVisibilityList { state.mirrors }.animatedAsGridAtLaunch(cells = cellsCount)

    LazyVerticalStaggeredGrid(
        StaggeredGridCells.Fixed(cellsCount),
        modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        verticalItemSpacing = MaterialTheme.spacing.large
    ) {
        fullLineItem {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.mirrors))
            })
        }
        visibilityItems(
            animatedMirrors
        ) { mirror ->
            MirrorCard(
                mirror = mirror,
                selected = state.selectedMirror == mirror,
                onClick = {
                    state.onEvent(MirrorViewModel.MirrorScreenEvent.SelectMirror(mirror))
                }
            )
        }
    }
}

