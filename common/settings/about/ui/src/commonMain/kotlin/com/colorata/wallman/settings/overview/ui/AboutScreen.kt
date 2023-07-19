package com.colorata.wallman.settings.overview.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.GroupedColumn
import com.colorata.animateaslifestyle.material3.shapes.ScallopShape
import com.colorata.animateaslifestyle.shapes.Arc
import com.colorata.animateaslifestyle.shapes.ExperimentalShapeApi
import com.colorata.animateaslifestyle.shapes.Full
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.*
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.ui.R
import com.colorata.wallman.core.ui.components.ArcBorder
import com.colorata.wallman.core.ui.modifiers.detectRotation
import com.colorata.wallman.core.ui.modifiers.displayRotation
import com.colorata.wallman.core.ui.modifiers.rememberRotationState
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.settings.about.api.AboutDestination
import com.colorata.wallman.settings.overview.ui.components.AboutItem
import com.colorata.wallman.settings.overview.viewmodel.AboutViewModel
import com.colorata.wallman.ui.icons.BugReport
import com.colorata.wallman.ui.icons.Code
import com.colorata.wallman.ui.icons.CurrencyRuble
import kotlinx.collections.immutable.persistentListOf

context(CoreModule)
fun MaterialNavGraphBuilder.aboutScreen() {
    continuousComposable(Destinations.AboutDestination()) {
        AboutScreen()
    }
}

context(CoreModule)
@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { AboutViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    AboutScreen(state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
@Composable
private fun AboutScreen(state: AboutViewModel.AboutScreenState, modifier: Modifier = Modifier) {
    val animatedItems =
        remember(state.aboutItems) { state.aboutItems.toStaggerList({ 0f }, false) }
    val defaultAnimation =
        fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
            animationSpec = MaterialTheme.animation.emphasized()
        )

    LaunchedEffect(key1 = Unit) {
        animatedItems.animateAsList(this, spec = staggerSpecOf {
            visible = true
        })
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        LargeTopAppBar(title = {
            Text(text = rememberString(Strings.aboutWallMan))
        })
        Logo(
            Modifier
                .zIndex(3f)
                .animateVisibility(
                    animatedItems[0].visible,
                    defaultAnimation
                )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        GroupedColumn(
            animatedItems,
            Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .clip(MaterialTheme.shapes.extraLarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            outerCorner = MaterialTheme.spacing.large
        ) {
            AboutItem(
                item = it.value,
                modifier = Modifier.animateVisibility(it.visible, defaultAnimation)
            )
        }
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .height(MaterialTheme.spacing.medium)
        )
    }
}

@OptIn(ExperimentalShapeApi::class)
@Composable
private fun Logo(modifier: Modifier = Modifier) {
    val shape = remember { ScallopShape(density = 100f) }
    val rotationState = rememberRotationState()
    Box(
        modifier
            .size(300.dp)
            .detectRotation(rotationState)
    ) {
        Box(
            Modifier
                .displayRotation(rotationState)
                .clip(shape)
                .background(Color(0xFF2F3032))
                .fillMaxSize()
        )
        val width by remember { derivedStateOf { if (rotationState.isRotationInProgress) 8f else 1f } }
        val animatedWidth =
            animateFloatAsState(width, label = "").value * MaterialTheme.spacing.extraSmall
        ArcBorder(
            Arc.Full,
            BorderStroke(animatedWidth, MaterialTheme.colorScheme.primary),
            Modifier
                .displayRotation(rotationState, layer = 1f)
                .fillMaxSize(),
            shape
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.3f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
private fun AboutScreenPreview() {
    WallManPreviewTheme {
        val state = remember {
            AboutViewModel.AboutScreenState(
                aboutItems = persistentListOf(AboutViewModel.AboutItem(
                    Strings.versionOfWallMan,
                    Strings.actualVersion,
                    Icons.Default.Info
                ) {
                }, AboutViewModel.AboutItem(
                    Strings.developer,
                    Strings.colorata,
                    Icons.Default.Person
                ) {
                }, AboutViewModel.AboutItem(
                    Strings.groupInTelegram,
                    Strings.tapToOpen,
                    Icons.Default.Send
                ) {
                }, AboutViewModel.AboutItem(
                    Strings.gitlab,
                    Strings.tapToOpen,
                    Icons.Default.Code
                ) {
                }, AboutViewModel.AboutItem(
                    Strings.supportWithQiwi,
                    Strings.tapToOpen,
                    Icons.Default.CurrencyRuble
                ) {
                }, AboutViewModel.AboutItem(
                    Strings.reportBug,
                    Strings.requiresAccountInGitlab,
                    Icons.Default.BugReport
                ) {
                }),
                clicksOnVersion = 0
            ) { }
        }
        AboutScreen(state = state)
    }
}