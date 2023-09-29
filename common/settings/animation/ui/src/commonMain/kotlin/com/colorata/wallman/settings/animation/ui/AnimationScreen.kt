package com.colorata.wallman.settings.animation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.wallman.core.data.AnimationType
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.continuousComposable
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.animation.animateVisibility
import com.colorata.wallman.core.ui.components.PreviewContainer
import com.colorata.wallman.core.ui.components.Text
import com.colorata.wallman.core.ui.components.VerticalSelector
import com.colorata.wallman.core.ui.list.VisibilityList
import com.colorata.wallman.core.ui.list.animatedAtLaunch
import com.colorata.wallman.core.ui.list.rememberVisibilityList
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.emphasizedEnterExit
import com.colorata.wallman.core.ui.theme.screenPadding
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration
import com.colorata.wallman.settings.animation.api.AnimationScreen
import com.colorata.wallman.settings.animation.viewmodel.AnimationEvent
import com.colorata.wallman.settings.animation.viewmodel.AnimationState
import com.colorata.wallman.settings.animation.viewmodel.AnimationViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay

context(CoreModule)
fun MaterialNavGraphBuilder.animationScreen() {
    continuousComposable(Destinations.AnimationScreen()) {
        AnimationScreen()
    }
}

context(CoreModule)
@Composable
fun AnimationScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { AnimationViewModel() }
    val state by viewModel.state.collectAsState()
    AnimationScreen(state, modifier)
}

@Composable
fun AnimationScreen(state: AnimationState, modifier: Modifier = Modifier) {
    val screenSize = LocalWindowSizeConfiguration.current
    val types = remember(state.availableTypes) {
        state.availableTypes.map { stringForAnimationType(it) }.toImmutableList()
    }
    val selectedTypeIndex = remember(state.selectedAnimationType) {
        state.availableTypes.indexOf(state.selectedAnimationType)
    }
    val onClick = { index: Int ->
        state.onEvent(AnimationEvent.SelectAnimationType(state.availableTypes[index]))
    }
    val visible = rememberVisibilityList(2).animatedAtLaunch()
    if (screenSize.isCompact()) {
        CompactScreen(types, selectedTypeIndex, onClick, visible, modifier)
    } else {
        ExpandedScreen(types, selectedTypeIndex, onClick, visible, modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactScreen(
    types: ImmutableList<Polyglot>,
    selectedTypeIndex: Int,
    onClick: (Int) -> Unit,
    visible: VisibilityList<*>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        LargeTopAppBar(title = { Text(rememberString(Strings.animations)) })
        AnimationPreview(
            selectedTypeIndex,
            Modifier
                .animateVisibility(
                    visible.visible[0],
                    MaterialTheme.animation.emphasizedEnterExit()
                )
                .height(300.dp)
        )
        VerticalSelector(
            values = types,
            selected = selectedTypeIndex,
            onClick = onClick,
            Modifier.animateVisibility(
                visible.visible[1],
                MaterialTheme.animation.emphasizedEnterExit()
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedScreen(
    types: ImmutableList<Polyglot>,
    selectedTypeIndex: Int,
    onClick: (Int) -> Unit,
    visible: VisibilityList<*>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(horizontal = MaterialTheme.spacing.screenPadding),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        LargeTopAppBar(title = { Text(rememberString(Strings.animations)) })
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            AnimationPreview(
                selectedTypeIndex,
                Modifier
                    .animateVisibility(
                        visible.visible[0],
                        MaterialTheme.animation.emphasizedEnterExit()
                    )
                    .weight(1f)
                    .fillMaxHeight()
            )
            VerticalSelector(
                types, selectedTypeIndex, onClick = onClick, Modifier
                    .animateVisibility(
                        visible.visible[0],
                        MaterialTheme.animation.emphasizedEnterExit()
                    )
                    .weight(1f)
            )
        }
    }
}

fun stringForAnimationType(animationType: AnimationType): Polyglot {
    return when (animationType) {
        AnimationType.Slide -> Strings.slide
        AnimationType.Scale -> Strings.scale
        AnimationType.Fade -> Strings.fade
    }
}

@Composable
private fun AnimationPreview(selectedTypeIndex: Int, modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            visible = !visible
            delay(2000)
        }
    }
    PreviewContainer(selectedTypeIndex, modifier) {
        Box(
            Modifier
                .animateVisibility(visible, MaterialTheme.animation.emphasizedEnterExit())
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(100.dp)
        )
    }
}

@LightDarkPreview
@Composable
private fun AnimationScreenPreview() {
    WallManPreviewTheme {
        var selected by remember { mutableStateOf(AnimationType.Slide) }
        AnimationScreen(AnimationState(selected, AnimationType.entries.toImmutableList()) {
            when (it) {
                is AnimationEvent.SelectAnimationType -> selected = it.type
            }
        })
    }
}