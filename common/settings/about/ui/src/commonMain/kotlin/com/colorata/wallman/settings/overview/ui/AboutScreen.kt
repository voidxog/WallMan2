package com.colorata.wallman.settings.overview.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.GroupedColumn
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.*
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.ui.R
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.data.viewModel
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
    var logoVisible by remember { mutableStateOf(false) }
    val animatedItems =
        remember(state.aboutItems) { state.aboutItems.toStaggerList({ 0f }, false) }
    val defaultAnimation =
        fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
            animationSpec = MaterialTheme.animation.emphasized()
        )

    LaunchedEffect(key1 = Unit) {
        logoVisible = true
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
        Image(
            painter = painterResource(id = R.drawable.ic_monochrome),
            contentDescription = "",
            modifier = Modifier
                .animateVisibility(
                    animatedItems[0].visible,
                    defaultAnimation
                )
                .clip(CircleShape)
                .background(Color(0xFF2F3032))
                .size(200.dp),
            contentScale = ContentScale.Crop
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