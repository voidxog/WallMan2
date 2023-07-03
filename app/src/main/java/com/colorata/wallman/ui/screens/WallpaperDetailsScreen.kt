package com.colorata.wallman.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.shapes.ScallopShape
import com.colorata.animateaslifestyle.shapes.ArcBorder
import com.colorata.animateaslifestyle.shapes.ExperimentalShapeApi
import com.colorata.animateaslifestyle.shapes.arc
import com.colorata.animateaslifestyle.shapes.degrees
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.arch.Chip
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.flatComposable
import com.colorata.wallman.arch.formatted
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.simplifiedLocaleOf
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.ui.LightDarkPreview
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.icons.SdCard
import com.colorata.wallman.ui.presets.BigChip
import com.colorata.wallman.ui.presets.WallpaperVariants
import com.colorata.wallman.ui.theme.LocalAnimation
import com.colorata.wallman.ui.theme.WallManPreviewTheme
import com.colorata.wallman.ui.theme.animation
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.viewmodels.WallpaperDetailsViewModel
import com.colorata.wallman.wallpaper.BaseWallpaper
import com.colorata.wallman.wallpaper.DynamicWallpaper
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.sizeInMb
import com.colorata.wallman.wallpaper.supportsDynamicWallpapers
import com.colorata.wallman.wallpaper.walls
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import soup.compose.material.motion.MaterialMotion
import soup.compose.material.motion.animation.materialSharedAxisX
import soup.compose.material.motion.animation.materialSharedAxisY

fun MaterialNavGraphBuilder.wallpaperDetailsScreen() {
    flatComposable(WallpaperDetailsViewModel.WallpaperDetailsScreenDestination) { entry ->
        val hashCode =
            entry.arguments?.getInt(WallpaperDetailsViewModel.WallpaperDetailsScreenDestination.arguments[0].name)
                ?: 0
        WallpaperDetailsScreen(wallpaperHashCode = hashCode)
    }
}

@Composable
fun WallpaperDetailsScreen(wallpaperHashCode: Int, modifier: Modifier = Modifier) {
    val viewModel = viewModel { WallpaperDetailsViewModel(wallpaperHashCode) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state) {
        println(state)
    }
    WallpaperDetailsScreen(state, modifier)
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
private fun WallpaperDetailsScreen(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    modifier: Modifier = Modifier
) {
    val wallpaper = state.wallpaper
    val selectedBaseWallpaper = state.selectedWallpaper
    val isPreview = LocalInspectionMode.current
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val animList = remember {
            List(8) { }.toStaggerList({ 0f }, visible = isPreview)
        }
        LaunchedEffect(Unit) {
            animList.animateAsList(this, spec = staggerSpecOf {
                visible = true
            })
        }
        val animationSpec =
            fade(animationSpec = animation.emphasized()) + slideVertically(animationSpec = animation.emphasized())
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.extraLarge)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
        ) {
            PreviewImage(
                resource = selectedBaseWallpaper.previewRes,
                downloadProgress = { state.downloadProgress },
                Modifier.animateVisibility(
                    animList[0].visible,
                    animationSpec
                )
            )
            PreviewName(
                selectedBaseWallpaper.previewName,
                Modifier
                    .align(Alignment.Start)
                    .animateVisibility(
                        animList[1].visible,
                        animationSpec
                    )
            )
            Description(
                selectedBaseWallpaper.description,
                Modifier
                    .align(Alignment.Start)
                    .animateVisibility(
                        animList[2].visible,
                        animationSpec
                    )
            )
            WallpaperTypeSelector(
                supportsDynamicWallpaper = remember(wallpaper) { wallpaper.supportsDynamicWallpapers() },
                selectedWallpaperType = state.selectedWallpaperType,
                onClick = {
                    state.onEvent(
                        WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.SelectWallpaperType(
                            it
                        )
                    )
                },
                Modifier.animateVisibility(
                    animList[3].visible,
                    fade(animationSpec = animation.emphasized()) + slideVertically(
                        100f,
                        animation.emphasized()
                    )
                )
            )
            val chips = remember {
                persistentListOf<Chip>().mutate {
                    if (selectedBaseWallpaper.coordinates != null) it.add(
                        Chip(
                            Strings.goToMaps,
                            Icons.Default.LocationOn
                        )
                    )
                    if (wallpaper.supportsDynamicWallpapers()) it.add(
                        Chip(
                            Strings.size.formatted(wallpaper.parent.sizeInMb()),
                            Icons.Default.SdCard
                        )
                    )
                    it.add(
                        Chip(
                            simplifiedLocaleOf(wallpaper.author),
                            Icons.Filled.AccountCircle
                        )
                    )
                }
            }
            Chips(
                chips = chips, onClick = {
                    val index = chips.indexOf(it)
                    if (index == 0) state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.GoToMaps)
                }, Modifier.animateVisibility(
                    animList[4].visible,
                    animationSpec
                )
            )
            Variants(
                state.wallpaperVariants,
                selectedWallpaper = state.selectedWallpaper,
                onClick = {
                    state.onEvent(
                        WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.SelectBaseWallpaper(it)
                    )
                },
                Modifier.animateVisibility(
                    animList[5].visible,
                    animationSpec
                )
            )
        }
        BottomBar(state)
    }
}

@LightDarkPreview
@Composable
private fun WallpaperDetailsPreview() {
    WallManPreviewTheme {
        WallpaperDetailsScreen(
            WallpaperDetailsViewModel.WallpaperDetailsScreenState(
                walls[42],
                actionType = WallpaperI.ActionType.Install(true),
                cacheState = DynamicWallpaper.DynamicWallpaperCacheState.Cached,
                downloadProgress = 50f,
                selectedWallpaper = walls[42].dynamicWallpapers[0],
                selectedWallpaperType = WallpaperI.SelectedWallpaperType.Dynamic,
                wallpaperVariants = walls[42].staticWallpapers,
                onEvent = { }
            ))
    }
}

@Composable
private fun Variants(
    wallpaperVariants: ImmutableList<BaseWallpaper>,
    selectedWallpaper: BaseWallpaper,
    onClick: (updatedWallpaper: BaseWallpaper) -> Unit,
    modifier: Modifier = Modifier
) {
    val variants =
        remember(wallpaperVariants) {
            wallpaperVariants.map { it.previewRes }.toImmutableList()
        }
    AnimatedContent(
        targetState = variants,
        transitionSpec = { materialSharedAxisX(true, 100) using SizeTransform(clip = false) },
        label = "",
        modifier = modifier
    ) { updatedVariants ->
        WallpaperVariants(
            wallpapers = updatedVariants,
            selectedWallpaper = selectedWallpaper.previewRes,
            onClick = { updatedRes ->
                onClick(
                    wallpaperVariants.first { it.previewRes == updatedRes }
                )
            }
        )
    }
}

@Composable
private fun PreviewImage(
    @DrawableRes resource: Int,
    downloadProgress: () -> Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .statusBarsPadding()
            .fillMaxWidth(0.7f)
    ) {
        val progress by animateFloatAsState(downloadProgress(), label = "")
        val start = if (progress != 100f) rememberInfiniteTransition(label = "").animateFloat(
            initialValue = -90f,
            targetValue = 270f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ), label = ""
        ).value else -90f
        ArcBorder(
            arc = arc(
                degrees(
                    start
                ), degrees(3.6f * progress)
            ),
            border = BorderStroke(
                MaterialTheme.spacing.extraSmall * animateFloatAsState(targetValue = if (progress == 100f) 1f else 3f).value,
                MaterialTheme.colorScheme.primary
            ),
            shape = ScallopShape(density = 100f)
        ) {
            AnimatedContent(
                targetState = resource,
                transitionSpec = {
                    materialSharedAxisX(true, 100)
                }, label = ""
            ) { res ->
                Image(
                    painter = painterResource(res),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun PreviewName(previewName: Polyglot, modifier: Modifier = Modifier) {
    AnimatedContent(
        targetState = previewName, transitionSpec = {
            materialSharedAxisX(true, 100) using SizeTransform(clip = false)
        },
        modifier = modifier, label = ""
    ) { name ->
        Text(
            text = rememberString(string = name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun Description(description: Polyglot, modifier: Modifier = Modifier) {
    AnimatedContent(
        targetState = description, transitionSpec = {
            materialSharedAxisX(true, 100) using SizeTransform(clip = false)
        },
        modifier = modifier, label = ""
    ) { animatedDescription ->
        Text(
            text = rememberString(string = animatedDescription),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Chips(
    chips: ImmutableList<Chip>,
    onClick: (Chip) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        chips.forEach { chip ->
            AssistChip(onClick = { onClick(chip) }, label = {
                Text(text = rememberString(chip.previewName))
            }, leadingIcon = {
                Icon(imageVector = chip.icon, contentDescription = "")
            })
        }
    }
}

@Composable
private fun WallpaperTypeSelector(
    supportsDynamicWallpaper: Boolean,
    selectedWallpaperType: WallpaperI.SelectedWallpaperType,
    onClick: (WallpaperI.SelectedWallpaperType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
    ) {
        if (supportsDynamicWallpaper) {
            BigChip(
                onClick = {
                    onClick(WallpaperI.SelectedWallpaperType.Dynamic)
                },
                selected = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Dynamic,
                text = WallpaperI.SelectedWallpaperType.Dynamic.label,
                Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        BigChip(
            onClick = {
                onClick(WallpaperI.SelectedWallpaperType.Static)
            },
            selected = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Static,
            text = WallpaperI.SelectedWallpaperType.Static.label,
            Modifier.weight(1f)
        )
    }
}

@Composable
private fun BottomBar(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = MaterialTheme.spacing.extraLarge,
                vertical = MaterialTheme.spacing.large
            )
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.ClickOnDownload)
            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(
                MaterialTheme.spacing.extraLarge,
                MaterialTheme.spacing.extraSmall,
                MaterialTheme.spacing.extraSmall,
                MaterialTheme.spacing.extraLarge
            ),
            enabled = state.selectedWallpaperType == WallpaperI.SelectedWallpaperType.Dynamic && state.wallpaper.supportsDynamicWallpapers()
        ) {
            MaterialMotion(targetState = state.cacheState, transitionSpec = {
                materialSharedAxisY(true, 100)
            }) {
                Text(text = rememberString(it.label))
            }
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
        Button(
            enabled = state.actionType.available, onClick = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.ClickOnActionButton)
            }, modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(
                MaterialTheme.spacing.extraSmall,
                MaterialTheme.spacing.extraLarge,
                MaterialTheme.spacing.extraLarge,
                MaterialTheme.spacing.extraSmall
            )
        ) {
            MaterialMotion(targetState = state.selectedWallpaperType.icon, transitionSpec = {
                materialSharedAxisY(true, 100)
            }) {
                Icon(imageVector = it, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            MaterialMotion(targetState = state.actionType.label, transitionSpec = {
                materialSharedAxisY(true, 100)
            }) {
                Text(text = rememberString(string = it))
            }
        }
    }
}