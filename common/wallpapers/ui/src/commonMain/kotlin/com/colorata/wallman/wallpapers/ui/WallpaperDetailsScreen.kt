package com.colorata.wallman.wallpapers.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.flatComposable
import com.colorata.wallman.core.data.icons.InstallMobile
import com.colorata.wallman.core.data.materialSharedAxisY
import com.colorata.wallman.core.data.parameter
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.viewModel
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.animation.animateVisibility
import com.colorata.wallman.core.ui.components.GradientType
import com.colorata.wallman.core.ui.components.ScreenBackground
import com.colorata.wallman.core.ui.list.animatedAtLaunch
import com.colorata.wallman.core.ui.list.rememberVisibilityList
import com.colorata.wallman.core.ui.modifiers.runWhen
import com.colorata.wallman.core.ui.shapes.RoundedCornerShapeStartEnd
import com.colorata.wallman.core.ui.theme.WallManContentTheme
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.emphasizedVerticalSlide
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.core.ui.util.LocalWindowSizeConfiguration
import com.colorata.wallman.wallpapers.DynamicWallpaper
import com.colorata.wallman.wallpapers.WallpaperDetailsDestination
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import com.colorata.wallman.wallpapers.ui.components.DescriptionAndActions
import com.colorata.wallman.wallpapers.ui.components.PreviewImage
import com.colorata.wallman.wallpapers.viewmodel.WallpaperDetailsViewModel
import com.colorata.wallman.wallpapers.walls

context(WallpapersModule)
fun MaterialNavGraphBuilder.wallpaperDetailsScreen() {
    flatComposable(Destinations.WallpaperDetailsDestination()) {
        val index by parameter()
        WallpaperDetailsScreen(wallpaperIndex = index?.toInt() ?: 0)
    }
}

context(WallpapersModule)
@Composable
fun WallpaperDetailsScreen(wallpaperIndex: Int, modifier: Modifier = Modifier) {
    val viewModel = viewModel { WallpaperDetailsViewModel(wallpaperIndex) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    WallpaperDetailsScreen(state, modifier)
}

@Composable
private fun WallpaperDetailsScreen(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    modifier: Modifier = Modifier
) {
    val selectedBaseWallpaper = state.selectedWallpaper

    val scrollState = rememberScrollState()
    val previewImage = bitmapAsset(state.selectedWallpaper.previewRes)
    val animationSpec = MaterialTheme.animation.emphasizedVerticalSlide()
    val animList = rememberVisibilityList {
        List(8) {}
    }.animatedAtLaunch()

    val windowSize = LocalWindowSizeConfiguration.current
    WallManContentTheme(state.selectedWallpaper.previewRes) {
        if (state.showPermissionRequest) {
            PermissionRequestDialog(onDismiss = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.DismissPermissionRequest)
            }, onConfirm = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.GoToInstallAppsPermissionsPage)
            })
        }

        if (state.showPerformanceWarning) {
            PerformanceWarningDialog(onDismiss = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.DismissPerformanceWarning)
            }, onConfirm = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.ProceedPerformanceWarning)
            })
        }

        Box(
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                ScreenBackground(previewImage)
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
                            .animateVisibility(animList.visible[0], animationSpec)
                    )
                    DescriptionAndActions(state, visibilityList = animList)
                }
                BottomBar(
                    state, Modifier
                        .align(Alignment.BottomCenter)
                        .animateVisibility(animList.visible[6], animationSpec)
                )
            } else {
                Row(Modifier.fillMaxSize()) {
                    Box(Modifier.weight(1f)) {
                        ScreenBackground(
                            previewImage,
                            gradientType = GradientType.Horizontal,
                            imageFraction = 1f
                        )
                        PreviewImage(
                            resource = selectedBaseWallpaper.previewRes,
                            downloadProgress = { state.downloadProgress },
                            Modifier
                                .padding(MaterialTheme.spacing.extraLarge)
                                .fillMaxSize()
                                .zIndex(3f)
                                .animateVisibility(animList.visible[0], animationSpec)
                        )
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(
                                Modifier
                                    .verticalScroll(scrollState)
                                    .padding(MaterialTheme.spacing.extraLarge),
                                verticalArrangement = Arrangement.Center
                            ) {
                                DescriptionAndActions(state, visibilityList = animList)
                            }
                        }
                        BottomBar(
                            state,
                            Modifier
                                .align(Alignment.BottomCenter)
                                .animateVisibility(animList.visible[6], animationSpec),
                            fullWidth = false
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
                onEvent = { })
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
fun PerformanceWarningDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, confirmButton = {
        Button(onClick = { onConfirm() }) {
            Text(rememberString(Strings.download))
        }
    }, dismissButton = {
        OutlinedButton(onClick = { onDismiss() }) {
            Text(rememberString(Strings.cancel))
        }
    }, icon = {
        Icon(Icons.Default.Warning, contentDescription = null)
    }, title = {
        Text(rememberString(Strings.performanceWarning))
    }, text = {
        Text(rememberString(Strings.performanceWarningDescription))
    }, modifier = modifier)
}

@Composable
private fun BottomBar(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    modifier: Modifier = Modifier,
    fullWidth: Boolean = true,
) {
    val spacing = MaterialTheme.spacing
    val horizontalPadding =
        if (fullWidth) spacing.extraLarge else spacing.medium
    val verticalPadding =
        if (fullWidth) spacing.large else spacing.small

    val transitionSpec = remember<AnimatedContentTransitionScope<*>.() -> ContentTransform> {
        {
            materialSharedAxisY(true, 100) using SizeTransform(false)
        }
    }
    Row(
        modifier
            .runWhen(!fullWidth) {
                widthIn(max = 400.dp)
                    .padding(
                        horizontal = spacing.extraLarge,
                        vertical = spacing.small
                    )
                    .navigationBarsPadding()
                    .clip(CircleShape)
            }
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .then(if (fullWidth) Modifier.navigationBarsPadding() else Modifier),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.ClickOnDownload)
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShapeStartEnd(
                MaterialTheme.spacing.extraLarge,
                MaterialTheme.spacing.extraSmall
            ),
            enabled = state.selectedWallpaperType == WallpaperI.SelectedWallpaperType.Dynamic &&
                    remember(state.wallpaper) { state.wallpaper.supportsDynamicWallpapers() }
        ) {
            AnimatedContent(
                targetState = state.cacheState,
                transitionSpec = transitionSpec,
                label = ""
            ) {
                Text(text = rememberString(it.label))
            }
        }

        Button(
            enabled = state.actionType.available,
            onClick = {
                state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.ClickOnActionButton)
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShapeStartEnd(
                MaterialTheme.spacing.extraSmall,
                MaterialTheme.spacing.extraLarge,
            ),
        ) {
            AnimatedContent(
                targetState = state.selectedWallpaperType.icon,
                transitionSpec = transitionSpec,
                label = ""
            ) {
                Icon(imageVector = it, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            AnimatedContent(
                targetState = state.actionType.label,
                transitionSpec = transitionSpec,
                label = ""
            ) {
                Text(text = rememberString(string = it))
            }
        }
    }
}