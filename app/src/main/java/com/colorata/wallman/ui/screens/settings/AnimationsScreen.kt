package com.colorata.wallman.ui.screens.settings

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.colorata.animateaslifestyle.animateBackground
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerItems
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.arch.LocalPaddings
import com.colorata.wallman.arch.continuousComposable
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.settings.AnimationsViewModel
import com.colorata.wallman.viewmodels.settings.CacheViewModel

fun MaterialNavGraphBuilder.animationsScreen() {
    continuousComposable(AnimationsViewModel.AnimationsScreenDestination) {
        AnimationsScreen()
    }
}

@Composable
fun AnimationsScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { AnimationsViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    AnimationsScreen(state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalStaggerApi::class)
@Composable
private fun AnimationsScreen(
    state: AnimationsViewModel.AnimationsScreenState,
    modifier: Modifier = Modifier
) {
    val ripple = remember { state.animationModes.toStaggerList({ 0f }, false) }
    LaunchedEffect(key1 = true) {
        ripple.animateAsList(this, spec = staggerSpecOf {
            visible = true
        })
    }
    LazyColumn(modifier) {
        item {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = com.colorata.wallman.arch.Strings.appMotion))
            })
        }
        staggerItems(ripple) {
            val background = MaterialTheme.colorScheme.run {
                if (it.value == state.currentAnimationMode)
                    primaryContainer
                else
                    surfaceVariant
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.large)
                    .animateBackground(
                        background,
                        shape = RoundedCornerShape(24.dp),
                        animationSpec = tween(500)
                    )
                    .clickable {
                        state.onEvent(
                            AnimationsViewModel.AnimationsScreenEvent.ClickOnAnimationMode(
                                it.value
                            )
                        )
                    }
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                CompositionLocalProvider(
                    LocalContentColor provides contentColorFor(
                        backgroundColor = background
                    )
                ) {
                    Text(
                        text = rememberString(string = it.value.name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = rememberString(string = it.value.description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Image(
                    painter = rememberAsyncImagePainter(
                        it.value.url,
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .aspectRatio(1.5f)
                        .fillMaxWidth()
                        .height(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(LocalPaddings.current.calculateBottomPadding()))
        }
    }
}