package com.colorata.wallman.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.flatComposable
import com.colorata.wallman.arch.launchIO
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.presets.FilteredWallpaperCards
import com.colorata.wallman.ui.theme.WallManPreviewTheme
import com.colorata.wallman.ui.theme.animation
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.MainViewModel
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.firstBaseWallpaper
import com.colorata.wallman.wallpaper.firstPreviewRes
import com.colorata.wallman.wallpaper.walls
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max

fun MaterialNavGraphBuilder.mainScreen() {
    flatComposable(MainViewModel.MainScreenDestination) { MainScreen() }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainScreen(state, modifier)
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
private fun MainScreen(state: MainViewModel.MainScreenState, modifier: Modifier = Modifier) {
    FilteredWallpaperCards(onClick = {
        state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
    }, wallpapers = remember(state.wallpapers) {
        state.wallpapers.toStaggerList(
            { 0f }, visible = false
        )
    }, startItem = {
        FeaturedWallpapers(state.featuredWallpapers, onClick = {
            state.onEvent(MainViewModel.MainScreenEvent.ClickOnWallpaper(it))
        }, Modifier.padding(vertical = MaterialTheme.spacing.extraLarge))
    }, name = rememberString(Strings.exploreNew), onRandomWallpaper = {
        state.onEvent(MainViewModel.MainScreenEvent.RandomWallpaper)
    }, modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalStaggerApi::class)
@Composable
private fun FeaturedWallpapers(
    wallpapers: ImmutableList<WallpaperI>,
    onClick: (WallpaperI) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPagerState(Int.MAX_VALUE / 2) {
        Int.MAX_VALUE
    }
    val anim = animation
    val scope = rememberCoroutineScope()
    val currentProgress = remember { Animatable(0f) }
    val visibleWallpapers = remember { wallpapers.toStaggerList({ 0f }, visible = false) }
    var indicatorsVisible by remember { mutableStateOf(false) }
    val transition = fade(animationSpec = anim.emphasized()) + slideVertically(
        from = 100f,
        animationSpec = anim.emphasized()
    )
    LaunchedEffect(Unit) {
        visibleWallpapers.animateAsList(
            this,
            startIndex = state.currentPage % wallpapers.size,
            spec = staggerSpecOf(itemsDelayMillis = 300) {
                visible = true
            })
        delay(300)
        indicatorsVisible = true
    }
    LaunchedEffect(Unit) {
        while (true) {
            launchIO({}) {
                val page = state.currentPage
                currentProgress.animateTo(
                    1f, animationSpec = tween(5000, easing = LinearEasing)
                )
                if (state.currentPage == page && currentProgress.value == 1f) state.animateScrollToPage(
                    state.currentPage + 1,
                    animationSpec = anim.emphasized(anim.durationSpec.extraLong4)
                )
                currentProgress.animateTo(0f)
            }.join()
            delay(1000)
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { state.isScrollInProgress }.collect {
            if (state.isScrollInProgress) {
                currentProgress.stop()
                currentProgress.animateTo(0f)
            }
        }
    }
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        HorizontalPager(
            state,
            contentPadding = PaddingValues(horizontal = 96.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(384.dp),
            pageSize = PageSize.Fixed(192.dp)
        ) { index ->
            val currentWallpaper = wallpapers[index % wallpapers.size]
            Box(
                Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                val cornerRadius = MaterialTheme.spacing.extraLarge
                Box(Modifier
                    .animateVisibility(
                        visibleWallpapers[index % wallpapers.size].visible,
                        transition
                    )
                    .clickable {
                        scope.launch {
                            if (state.currentPage != index) state.animateScrollToPage(
                                index,
                                animationSpec = anim.emphasized(anim.durationSpec.extraLong4)
                            ) else onClick(currentWallpaper)
                        }
                    }
                    .graphicsLayer {
                        val pageOffset = state.calculateOffsetForIndex(index)
                        alpha = lerp(0.5f, 1f, 1f - pageOffset)
                        scaleX = lerp(0.85f, 1f, 1f - pageOffset)
                        scaleY = lerp(0.85f, 1f, 1f - pageOffset)
                    }
                    .drawWithContent {
                        val pageOffset = state.calculateOffsetForIndex(index)
                        clipPath(Path().apply {
                            this.addRoundRect(
                                RoundRect(
                                    Rect(
                                        left = 48.dp.toPx() * pageOffset,
                                        right = size.width - 48.dp.toPx() * pageOffset,
                                        top = 0f,
                                        bottom = size.height
                                    ), CornerRadius(cornerRadius.toPx())
                                )
                            )
                        }, clipOp = ClipOp.Intersect) {
                            this@drawWithContent.drawContent()
                        }
                    }) {
                    Image(
                        painter = painterResource(id = currentWallpaper.firstPreviewRes()),
                        contentDescription = "",
                        Modifier
                            .align(Alignment.Center)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        Modifier
                            .graphicsLayer {
                                val pageOffset = state.calculateOffsetForIndex(index)
                                alpha = maxOf(1f - pageOffset * 2, 0f)
                            }
                            .align(
                                Alignment.BottomCenter
                            )
                            .padding(MaterialTheme.spacing.large)
                            .drawWithContent {
                                clipPath(Path().apply {
                                    val pageOffset = state.calculateOffsetForIndex(index)
                                    this.addRoundRect(
                                        RoundRect(
                                            Rect(
                                                left = size.width / 2 * pageOffset,
                                                right = size.width - size.width / 2 * pageOffset,
                                                top = 0f,
                                                bottom = size.height
                                            ), CornerRadius(size.height / 2)
                                        )
                                    )
                                }, clipOp = ClipOp.Intersect) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                            .heightIn(min = 48.dp)
                    ) {
                        Text(
                            rememberString(string = currentWallpaper.firstBaseWallpaper().shortName),
                            maxLines = 2,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Row(
            Modifier.animateVisibility(indicatorsVisible, transition),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            wallpapers.forEachIndexed { index, _ ->
                val pageOffset by remember {
                    derivedStateOf {
                        (state.currentPage % wallpapers.size - index + state.currentPageOffsetFraction).absoluteValue.coerceIn(
                            0f,
                            1f
                        )
                    }
                }
                val isSelected by remember { derivedStateOf { state.currentPage % wallpapers.size == index } }
                val animatedWidth by animateDpAsState(
                    12.dp + 24.dp * (1f - pageOffset),
                    label = ""
                )
                val animatedBackground by animateColorAsState(
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    label = ""
                )
                val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
                Box(
                    Modifier
                        .clip(CircleShape)
                        .drawBehind {
                            drawRect(surfaceVariant)
                            if (isSelected) drawRoundRect(
                                animatedBackground,
                                size = size.copy(width = size.width * currentProgress.value),
                                cornerRadius = CornerRadius(size.height / 2)
                            )
                        }
                        .width(animatedWidth)
                        .height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun PagerState.calculateOffsetForIndex(index: Int) =
    (currentPage - index + currentPageOffsetFraction)
        .absoluteValue.coerceIn(0f, 1f)

@Preview
@Composable
private fun FeaturedWallpapersPreview() {
    WallManPreviewTheme {
        FeaturedWallpapers(walls.subList(0, 10), onClick = {})
    }
}