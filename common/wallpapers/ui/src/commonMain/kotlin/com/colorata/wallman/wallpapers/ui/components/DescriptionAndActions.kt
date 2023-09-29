package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.formatted
import com.colorata.wallman.core.data.materialSharedAxisX
import com.colorata.wallman.core.data.mutate
import com.colorata.wallman.core.data.simplifiedLocaleOf
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.animation.animateVisibility
import com.colorata.wallman.core.ui.list.VisibilityList
import com.colorata.wallman.core.ui.list.animatedAtLaunch
import com.colorata.wallman.core.ui.list.rememberVisibilityList
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.emphasizedEnterExit
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.ui.icons.SdCard
import com.colorata.wallman.wallpapers.BaseWallpaper
import com.colorata.wallman.wallpapers.Chip
import com.colorata.wallman.wallpapers.DynamicWallpaper
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.firstBaseWallpaper
import com.colorata.wallman.wallpapers.sizeInMb
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import com.colorata.wallman.wallpapers.viewmodel.WallpaperDetailsViewModel
import com.colorata.wallman.wallpapers.walls
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun DescriptionAndActions(
    state: WallpaperDetailsViewModel.WallpaperDetailsScreenState,
    visibilityList: VisibilityList<Unit>,
    modifier: Modifier = Modifier
) {
    val animationSpec = MaterialTheme.animation.emphasizedEnterExit()
    val wallpaper = state.wallpaper
    val selectedBaseWallpaper = state.selectedWallpaper
    Column(
        modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        PreviewName(
            selectedBaseWallpaper.previewName, Modifier
                .align(Alignment.Start)
                .animateVisibility(visibilityList.visible[1], animationSpec)
        )
        Description(
            selectedBaseWallpaper.description, Modifier
                .align(Alignment.Start)
                .animateVisibility(visibilityList.visible[2], animationSpec)
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
            Modifier.animateVisibility(visibilityList.visible[3], animationSpec),
            disableNotSelected = state.actionType == WallpaperI.ActionType.Installing
        )
        val chips = remember {
            persistentListOf<Chip>().mutate {
                if (selectedBaseWallpaper.coordinates != null) add(
                    Chip(
                        Strings.goToMaps,
                        Icons.Default.LocationOn
                    )
                )
                if (wallpaper.supportsDynamicWallpapers()) add(
                    Chip(
                        Strings.size.formatted(
                            wallpaper.parent.sizeInMb()
                        ), Icons.Default.SdCard
                    )
                )
                add(Chip(simplifiedLocaleOf(wallpaper.author), Icons.Filled.AccountCircle))
            }.toImmutableList()
        }
        Chips(chips = chips, onClick = {
            val index = chips.indexOf(it)
            if (index == 0) state.onEvent(WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.GoToMaps)
        }, Modifier.animateVisibility(visibilityList.visible[4], animationSpec))
        Variants(
            state.wallpaperVariants,
            selectedWallpaper = state.selectedWallpaper,
            onClick = {
                state.onEvent(
                    WallpaperDetailsViewModel.WallpaperDetailsScreenEvent.SelectBaseWallpaper(it)
                )
            },
            Modifier.animateVisibility(visibilityList.visible[5], animationSpec),
            disableNotSelected = state.actionType == WallpaperI.ActionType.Installing
        )
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun Variants(
    wallpaperVariants: ImmutableList<BaseWallpaper>,
    selectedWallpaper: BaseWallpaper,
    onClick: (updatedWallpaper: BaseWallpaper) -> Unit,
    modifier: Modifier = Modifier,
    disableNotSelected: Boolean = false
) {
    val variants = remember(wallpaperVariants) {
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
                onClick(wallpaperVariants.first { it.previewRes == updatedRes })
            },
            disableNotSelected = disableNotSelected
        )
    }
}

@LightDarkPreview
@Composable
private fun DescriptionAndActionsPreview() {
    WallManPreviewTheme {
        DescriptionAndActions(
            WallpaperDetailsViewModel.WallpaperDetailsScreenState(
                actionType = WallpaperI.ActionType.Install(available = true),
                cacheState = DynamicWallpaper.DynamicWallpaperCacheState.Cached,
                downloadProgress = 100f,
                selectedWallpaper = walls[0].firstBaseWallpaper(),
                selectedWallpaperType = WallpaperI.SelectedWallpaperType.Dynamic,
                wallpaper = walls[0],
                wallpaperVariants = persistentListOf()
            ) {}, visibilityList = rememberVisibilityList(count = 10).animatedAtLaunch()
        )
    }
}