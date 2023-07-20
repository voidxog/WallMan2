package com.colorata.wallman.settings.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.animateaslifestyle.slideVertically
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
import com.colorata.wallman.core.ui.modifiers.navigationPadding
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.util.fullLineItem
import com.colorata.wallman.core.ui.util.rememberWindowSize
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
@Composable
private fun SettingsScreen(
    state: SettingsViewModel.SettingsScreenState,
    modifier: Modifier = Modifier
) {
    val animatedItems = remember { state.settingsItems.toStaggerList({ 0f }, false) }
    LaunchedEffect(key1 = true) {
        animatedItems.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }
    val transition = fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
        100f,
        animationSpec = MaterialTheme.animation.emphasized()
    )
    val windowSize = rememberWindowSize()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(if (windowSize.isCompact()) 1 else 2),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.large),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalItemSpacing = MaterialTheme.spacing.medium
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
                        transition
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