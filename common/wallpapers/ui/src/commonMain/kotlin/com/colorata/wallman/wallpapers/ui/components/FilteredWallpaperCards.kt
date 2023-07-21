package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.material3.fab.AnimatedFloatingActionButton
import com.colorata.animateaslifestyle.material3.fab.FabSize
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.animateaslifestyle.stagger.StaggerList
import com.colorata.animateaslifestyle.stagger.animateAsGrid
import com.colorata.animateaslifestyle.stagger.asStaggerList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.ui.components.ScreenBackground
import com.colorata.wallman.core.ui.modifiers.Padding
import com.colorata.wallman.core.ui.modifiers.runWhen
import com.colorata.wallman.core.ui.modifiers.navigationBottomPadding
import com.colorata.wallman.core.ui.modifiers.navigationStartPadding
import com.colorata.wallman.core.ui.modifiers.withoutHorizontalPadding
import com.colorata.wallman.core.ui.theme.emphasizedVerticalSlide
import com.colorata.wallman.core.ui.theme.screenPadding
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration
import com.colorata.wallman.ui.icons.Shuffle
import com.colorata.wallman.wallpapers.WallpaperI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun FilteredWallpaperCards(
    onClick: (WallpaperI) -> Unit,
    name: String,
    modifier: Modifier = Modifier,
    startItem: @Composable (LazyGridItemScope.() -> Unit)? = null,
    description: String = "",
    wallpapers: StaggerList<WallpaperI, Float>,
    onRandomWallpaper: () -> Unit,
    backgroundImageBitmap: ImageBitmap? = null,
    applyNavigationPadding: Boolean = false
) {
    val windowSize = LocalWindowSizeConfiguration.current
    var fabHeight by remember { mutableStateOf(0.dp) }
    val sortedWallpapers =
        remember(wallpapers) {
            wallpapers.asStaggerList()
        }
    val density = LocalDensity.current
    val listDensity = remember(windowSize) {
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> 2
            WindowWidthSizeClass.Medium -> 3
            else -> 4
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyGridState()
        var selectedIndex: Int? by remember { mutableStateOf(null) }
        val animatable = remember { androidx.compose.animation.core.Animatable(1f) }
        LaunchedEffect(key1 = selectedIndex) {
            if (selectedIndex != null) animatable.animateTo(1.1f, tween(200, easing = EaseInSine))
            else animatable.animateTo(1.0f, tween(100, easing = EaseInSine))
        }
        LaunchedEffect(key1 = sortedWallpapers) {
            sortedWallpapers.forEachIndexed { index, element ->
                element.visible = index !in state.layoutInfo.visibleItemsInfo.map { it.index }
            }
            sortedWallpapers.animateAsGrid(
                this,
                cells = listDensity,
                startIndex = if (state.firstVisibleItemIndex !in sortedWallpapers.indices) sortedWallpapers.lastIndex else state.firstVisibleItemIndex,
                spec = staggerSpecOf(itemsDelayMillis = 100) {
                    visible = true
                }
            )
        }
        var backgroundOffset by remember { mutableFloatStateOf(0f) }
        if (backgroundImageBitmap != null) {
            ScreenBackground(
                backgroundImageBitmap,
                imageFraction = if (windowSize.isCompact()) 0.5f else 0.8f,
                modifier = Modifier.graphicsLayer {
                    translationY = backgroundOffset
                }
            )
        }
        val startPadding = if (applyNavigationPadding) Padding.navigationStartPadding() else 0.dp
        LazyVerticalGrid(
            columns = GridCells.Fixed(listDensity),
            state = state,
            contentPadding = PaddingValues(
                bottom = fabHeight,
                start = MaterialTheme.spacing.screenPadding + startPadding,
                end = MaterialTheme.spacing.screenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            modifier = Modifier.testTag("WallpaperList")
        ) {
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Column(Modifier.onGloballyPositioned {
                    backgroundOffset = it.positionInParent().y
                }) {
                    LargeTopAppBar(
                        title = {
                            androidx.compose.material3.Text(text = name)
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent)
                    )
                    if (description != "") {
                        androidx.compose.material3.Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(
                                start = MaterialTheme.spacing.large,
                                end = MaterialTheme.spacing.large,
                                bottom = MaterialTheme.spacing.large
                            )
                        )
                    }
                    if (startItem != null) {
                        Box(
                            Modifier.withoutHorizontalPadding(
                                start = MaterialTheme.spacing.screenPadding + startPadding,
                                end = MaterialTheme.spacing.screenPadding
                            )
                        ) {
                            startItem()
                        }
                    }
                }
            }
            itemsIndexed(sortedWallpapers, key = { _, it ->
                it.hashCode()
            }) { index, it ->
                WallpaperCard(
                    wallpaper = it.value, modifier = Modifier
                        .graphicsLayer {
                            if (index == selectedIndex) animatable.value.let {
                                scaleX = it
                                scaleY = it
                            }
                        }
                        .animateItemPlacement()
                        .animateVisibility(
                            it.visible,
                            MaterialTheme.animation.emphasizedVerticalSlide()
                        )
                        .testTag("Wallpaper")
                ) {
                    scope.launch {
                        selectedIndex = index
                        delay(50)
                        onClick(it.value)
                    }
                }
            }
        }

        AnimatedFloatingActionButton(
            onClick = {
                scope.launch {
                    onRandomWallpaper()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .onSizeChanged {
                    fabHeight = density.run { it.height.toDp() }
                }
                .runWhen(applyNavigationPadding) { navigationBottomPadding() }
                .runWhen(!applyNavigationPadding) { navigationBarsPadding() }
                .padding(MaterialTheme.spacing.large),
            delayMillis = 200,
            durationMillis = MaterialTheme.animation.durationSpec.long2,
            size = FabSize.Large
        ) {
            Icon(imageVector = Icons.Default.Shuffle, contentDescription = "Shuffle")
        }
    }
}