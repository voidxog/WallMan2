package com.colorata.wallman.ui.screens.settings

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.material3.groupedItems
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.continuousComposable
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.presets.MirrorCard
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.settings.MirrorViewModel

data class Mirror(
    val name: Polyglot, val url: String
)

fun MaterialNavGraphBuilder.mirrorScreen() {
    continuousComposable(MirrorViewModel.MirrorScreenDestination) {
        MirrorScreen()
    }
}

@Composable
fun MirrorScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MirrorViewModel() }
    val state by viewModel.state.collectAsState()
    MirrorScreen(state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MirrorScreen(state: MirrorViewModel.MirrorScreenState, modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal = MaterialTheme.spacing.large)) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LargeTopAppBar(title = {
                    Text(text = rememberString(string = Strings.mirrors))
                })
            }
        }
        groupedItems(
            state.mirrors,
            Orientation.Vertical,
            padding = 4.dp,
            outerCorner = 20.dp
        ) { mirror ->
            MirrorCard(mirror = mirror, selected = state.selectedMirror == mirror, onClick = {
                state.onEvent(MirrorViewModel.MirrorScreenEvent.SelectMirror(mirror))
            })
        }
    }
}