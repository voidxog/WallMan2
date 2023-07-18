package com.colorata.wallman.wallpapers.ui

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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.material3.shapes.ScallopShape
import com.colorata.animateaslifestyle.shapes.ExperimentalShapeApi
import com.colorata.animateaslifestyle.shapes.arc
import com.colorata.animateaslifestyle.shapes.degrees
import com.colorata.animateaslifestyle.shapes.drawMaskArc
import com.colorata.animateaslifestyle.shapes.drawOffscreen
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.StaggerList
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.formatted
import com.colorata.wallman.core.data.icons.InstallMobile
import com.colorata.wallman.core.data.materialSharedAxisX
import com.colorata.wallman.core.data.materialSharedAxisY
import com.colorata.wallman.core.data.parameter
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.simplifiedLocaleOf
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.components.ScreenBackground
import com.colorata.wallman.core.ui.modifiers.rememberRotationState
import com.colorata.wallman.core.ui.modifiers.detectRotation
import com.colorata.wallman.core.ui.modifiers.displayRotation
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.theme.WallManContentTheme
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.util.rememberWindowSize
import com.colorata.wallman.ui.icons.SdCard
import com.colorata.wallman.wallpapers.BaseWallpaper
import com.colorata.wallman.wallpapers.Chip
import com.colorata.wallman.wallpapers.DynamicWallpaper
import com.colorata.wallman.wallpapers.WallpaperDetailsDestination
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.sizeInMb
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import com.colorata.wallman.wallpapers.ui.components.BigChip
import com.colorata.wallman.wallpapers.ui.components.WallpaperVariants
import com.colorata.wallman.wallpapers.viewmodel.WallpaperDetailsViewModel
import com.colorata.wallman.wallpapers.walls
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

context(WallpapersModule)
fun MaterialNavGraphBuilder.wallpaperDetailsScreen() {
    flatComposable(Destinations.WallpaperDetailsDestination()) {
        val hashCode by parameter()
        WallpaperDetailsScreen(wallpaperHashCode = hashCode?.toInt() ?: 0)
    }
}

context(WallpapersModule)
@Composable
fun WallpaperDetailsScreen(wallpaperHashCode: Int, modifier: Modifier = Modifier) {
    val viewModel = viewModel { WallpaperDetailsViewModel(wallpaperHashCode) }
    val state by viewModel.state.collectAsStateWithLifecycle()
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
    if (state.showPermissionRequest) {
        PermissionRequestDialog(onDismiss = {
            state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.DismissPermissionRequest)
        }, onConfirm = {
            state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.GoToInstallAppsPermissionsPage)
        })
    }
    val scrollState = rememberScrollState()
    val previewImage = bitmapAsset(state.selectedWallpaper.previewRes)
    val animationSpec =
        fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
            animationSpec = MaterialTheme.animation.emphasized()
        )
    val animList = remember {
        List(8) { }.toStaggerList({ 0f }, visible = isPreview)
    }
    LaunchedEffect(Unit) {
        animList.animateAsList(this, spec = staggerSpecOf {
            visible = true
        })
    }

    val windowSize = rememberWindowSize()
    ScreenBackground(previewImage)
    WallManContentTheme(state.selectedWallpaper.previewRes) {
        Box(
            modifier
                .fillMaxSize()
        ) {
            if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                Column(
                    Modifier
                        .verticalScroll(scrollState)
                        .padding(MaterialTheme.spacing.extraLarge)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                ) {
                    PreviewImage(
                        resource = selectedBaseWallpaper.previewRes,
                        downloadProgress = { state.downloadProgress },
                        Modifier
                            .zIndex(3f)
                            .animateVisibility(
                                animList[0].visible,
                                animationSpec
                            )
                    )
                    DescriptionAndActions(state, visibilityList = animList)
                }
                BottomBar(
                    state,
                    Modifier
                        .align(Alignment.BottomCenter)
                        .animateVisibility(
                            animList[6].visible,
                            animationSpec
                        )
                )
            } else {
                Row(Modifier.fillMaxSize()) {
                    PreviewImage(
                        resource = selectedBaseWallpaper.previewRes,
                        downloadProgress = { state.downloadProgress },
                        Modifier
                            .weight(1f)
                            .zIndex(3f)
                            .animateVisibility(
                                animList[0].visible,
                                animationSpec
                            )
                    )
                    Box(Modifier.weight(1f)) {
                        Column(
                            Modifier
                                .verticalScroll(scrollState)
                                .padding(MaterialTheme.spacing.extraLarge)
                                .fillMaxSize()
                        ) {
                            DescriptionAndActions(state, visibilityList = animList)
                        }
                        BottomBar(
                            state,
                            Modifier
                                .align(Alignment.BottomCenter)
                                .animateVisibility(
                                    animList[6].visible,
                                    animationSpec
                                )
                        )
                    }
                }
            }
        }
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

@OptIn(ExperimentalShapeApi::class)
@Composable
private fun PreviewImage(
    resource: String,
    downloadProgress: () -> Float,
    modifier: Modifier = Modifier
) {
    val shape = remember { ScallopShape(density = 100f) }
    val rotationState = rememberRotationState()
    Box(
        modifier
            .detectRotation(rotationState)
            .statusBarsPadding()
            .fillMaxWidth(0.8f),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = resource,
            transitionSpec = {
                materialSharedAxisX(true, 100)
            }, label = "",
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .displayRotation(rotationState)
                .clip(shape)
                .size(300.dp)
        ) { imageName ->
            Image(
                bitmap = bitmapAsset(imageName),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        Arc(
            progress = { downloadProgress() },
            width = { progress ->
                if (rotationState.isRotationInProgress) 8f
                else if (progress == 100f) 1f
                else 3f
            },
            Modifier
                .displayRotation(rotationState, layer = 1f)
                .size(300.dp),
            shape
        )
    }
}

@Composable
private fun Arc(
    progress: () -> Float,
    width: (progress: Float) -> Float,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape
) {
    val animatedProgress by animateFloatAsState(progress(), label = "")
    val start = if (animatedProgress != 100f) rememberInfiniteTransition(label = "").animateFloat(
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
    val animatedWidth =
        MaterialTheme.spacing.extraSmall * animateFloatAsState(
            targetValue = width(animatedProgress),
            label = ""
        ).value
    val borderColor = MaterialTheme.colorScheme.primary
    val layoutDirection = LocalLayoutDirection.current
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .clip(shape)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawOffscreen()
        ) {
            val border = BorderStroke(
                animatedWidth,
                borderColor
            )
            drawOutline(
                shape.createOutline(size, layoutDirection, density),
                brush = border.brush,
                style = Stroke(border.width.toPx(), cap = StrokeCap.Round)
            )
            drawMaskArc(arc(degrees(start), degrees(3.6f * animatedProgress)), border.brush)
        }
    }
}

@Composable
fun DescriptionAndActions(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    visibilityList: StaggerList<Unit, Float>,
    modifier: Modifier = Modifier
) {
    val animationSpec =
        fade(animationSpec = MaterialTheme.animation.emphasized()) + slideVertically(
            animationSpec = MaterialTheme.animation.emphasized()
        )
    val wallpaper = state.wallpaper
    val selectedBaseWallpaper = state.selectedWallpaper
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        PreviewName(
            selectedBaseWallpaper.previewName,
            Modifier
                .align(Alignment.Start)
                .animateVisibility(
                    visibilityList[1].visible,
                    animationSpec
                )
        )
        Description(
            selectedBaseWallpaper.description,
            Modifier
                .align(Alignment.Start)
                .animateVisibility(
                    visibilityList[2].visible,
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
                visibilityList[3].visible,
                animationSpec
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
                visibilityList[4].visible,
                animationSpec
            )
        )
        Variants(
            state.wallpaperVariants,
            selectedWallpaper = state.selectedWallpaper,
            onClick = {
                state.onEvent(
                    WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.SelectBaseWallpaper(
                        it
                    )
                )
            },
            Modifier.animateVisibility(
                visibilityList[5].visible,
                animationSpec
            )
        )
        Spacer(Modifier.height(80.dp))
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
fun PermissionRequestDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, confirmButton = {
        Button(onClick = { onConfirm() }) {
            Text(rememberString(Strings.ok))
        }
    }, icon = {
        Icon(Icons.Default.InstallMobile, contentDescription = null)
    }, title = {
        Text(rememberString(Strings.permissionNeeded))
    }, text = {
        Text(rememberString(Strings.permissionNeededDescription))
    }, modifier = modifier)
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
            AnimatedContent(targetState = state.cacheState, transitionSpec = {
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
            AnimatedContent(targetState = state.selectedWallpaperType.icon, transitionSpec = {
                materialSharedAxisY(true, 100)
            }) {
                Icon(imageVector = it, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            AnimatedContent(targetState = state.actionType.label, transitionSpec = {
                materialSharedAxisY(true, 100)
            }) {
                Text(text = rememberString(string = it))
            }
        }
    }
}