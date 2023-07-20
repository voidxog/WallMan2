package com.colorata.wallman.settings.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.module.CoreModule
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
import com.colorata.wallman.settings.overview.api.SettingsOverviewDestination
import com.colorata.wallman.settings.overview.ui.components.SettingsItem
import com.colorata.wallman.settings.overview.viewmodel.SettingsViewModel

context(CoreModule)
fun MaterialNavGraphBuilder.settingsScreen() {
    flatComposable(Destinations.SettingsOverviewDestination(), hasContinuousChildren = true) {
        SettingsScreen(Modifier.navigationPadding())
    }
}

context(CoreModule)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { SettingsViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(state, modifier)
}

@Composable
private fun SettingsScreen(
    state: SettingsViewModel.SettingsScreenState,
    modifier: Modifier = Modifier
) {
    // TODO: refactor when https://gitlab.com/colorata/wallman/-/issues/1 fixed
    val windowSize = LocalWindowSizeConfiguration.current
    if (windowSize.isCompact()) {
        SettingsScreenLayout(StaggeredGridCells.Fixed(1), state, modifier)
    } else {
        SettingsScreenLayout(StaggeredGridCells.Fixed(2), state, modifier)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
private fun SettingsScreenLayout(
    cells: StaggeredGridCells,
    state: SettingsViewModel.SettingsScreenState,
    modifier: Modifier = Modifier,
) {
    val animatedItems = remember { state.settingsItems.toStaggerList({ 0f }, false) }
    LaunchedEffect(key1 = true) {
        animatedItems.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }

    LazyVerticalStaggeredGrid(
        columns = cells,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalItemSpacing = MaterialTheme.spacing.medium,
        contentPadding = PaddingValues(bottom = Padding.navigationBarPadding())
    ) {
        fullLineItem {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.more))
            })
        }
        items(animatedItems) {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .height(IntrinsicSize.Max)
                    .animateVisibility(
                        it.visible,
                        MaterialTheme.animation.emphasizedVerticalSlide()
                    )
            ) {
                SettingsItem(
                    item = it.value,
                    onClick = {
                        state.onEvent(SettingsViewModel.SettingsScreenEvent.GoToSettings(it.value))
                    }
                )
            }
        }
    }
}