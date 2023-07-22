package com.colorata.wallman.settings.mirror.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.continuousComposable
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.theme.emphasizedVerticalSlide
import com.colorata.wallman.core.ui.theme.screenPadding
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration
import com.colorata.wallman.core.ui.util.fullLineItem
import com.colorata.wallman.settings.mirror.api.MirrorDestination
import com.colorata.wallman.settings.mirror.ui.components.MirrorCard
import com.colorata.wallman.settings.mirror.viewmodel.MirrorViewModel

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
            StaggeredGridCells.Fixed(1), state, modifier
        )
    } else {
        MirrorScreenLayout(
            StaggeredGridCells.Fixed(2), state, modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
private fun MirrorScreenLayout(
    cells: StaggeredGridCells,
    state: MirrorViewModel.MirrorScreenState,
    modifier: Modifier,
) {
    val animatedMirrors = remember { state.mirrors.toStaggerList({ 0f }, visible = false) }
    LaunchedEffect(key1 = true) {
        animatedMirrors.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }

    LazyVerticalStaggeredGrid(
        cells,
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
        items(
            animatedMirrors
        ) { animated ->
            val mirror = animated.value
            MirrorCard(
                mirror = mirror,
                selected = state.selectedMirror == mirror,
                onClick = {
                    state.onEvent(MirrorViewModel.MirrorScreenEvent.SelectMirror(mirror))
                },
                modifier = Modifier
                    .animateVisibility(
                        animated.visible,
                        MaterialTheme.animation.emphasizedVerticalSlide()
                    )
                    .clip(MaterialTheme.shapes.large)
            )
        }
    }
}
