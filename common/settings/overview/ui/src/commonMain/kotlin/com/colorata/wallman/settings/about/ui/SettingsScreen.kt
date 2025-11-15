package com.voidxog.wallman2.settings.about.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.flatComposable
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.viewModel
import com.voidxog.wallman2.core.ui.components.ListItem
import com.voidxog.wallman2.core.ui.list.animatedAsGridAtLaunch
import com.voidxog.wallman2.core.ui.list.rememberVisibilityList
import com.voidxog.wallman2.core.ui.list.visibilityItems
import com.voidxog.wallman2.core.ui.modifiers.Padding
import com.voidxog.wallman2.core.ui.modifiers.navigationBarPadding
import com.voidxog.wallman2.core.ui.modifiers.navigationPadding
import com.voidxog.wallman2.core.ui.theme.screenPadding
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.core.ui.util.LocalWindowSizeConfiguration
import com.voidxog.wallman2.core.ui.util.fullLineItem
import com.voidxog.wallman2.settings.overview.api.SettingsOverviewDestination
import com.voidxog.wallman2.settings.about.viewmodel.SettingsViewModel

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
        SettingsScreenLayout(1, state, modifier)
    } else {
        SettingsScreenLayout(2, state, modifier)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SettingsScreenLayout(
    cellsCount: Int,
    state: SettingsViewModel.SettingsScreenState,
    modifier: Modifier = Modifier,
) {
    val animatedItems =
        rememberVisibilityList { state.settingsItems }.animatedAsGridAtLaunch(cellsCount)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(cellsCount),
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
        visibilityItems(animatedItems) {
            ListItem(
                remember { ListItem(it.name, it.description, it.icon) },
                onClick = { state.onEvent(SettingsViewModel.SettingsScreenEvent.GoToSettings(it)) }, Modifier.fillMaxWidth())
        }
    }
}
