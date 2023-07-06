package com.colorata.wallman.settings.overview.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.groupedItems
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.viewModel
import com.colorata.wallman.settings.overview.api.SettingsOverviewDestination
import com.colorata.wallman.settings.overview.ui.components.SettingsItem
import com.colorata.wallman.settings.overview.viewmodel.SettingsViewModel

fun MaterialNavGraphBuilder.settingsScreen() {
    flatComposable(Destinations.SettingsOverviewDestination(), hasContinuousChildren = true) {
        SettingsScreen()
    }
}

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
    val transition = fade(animationSpec = animation.emphasized()) + slideVertically(
        100f,
        animationSpec = animation.emphasized()
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.large)
    ) {
        item {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.more))
            })
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
        groupedItems(animatedItems, Orientation.Vertical, padding = 4.dp, outerCorner = 20.dp) {
            Column(
                modifier = Modifier.animateVisibility(
                    it.visible,
                    transition
                )
            ) {
                SettingsItem(
                    item = it.value
                ) {
                    state.onEvent(SettingsViewModel.SettingsScreenEvent.GoToSettings(it.value))
                }
            }
        }
    }
}