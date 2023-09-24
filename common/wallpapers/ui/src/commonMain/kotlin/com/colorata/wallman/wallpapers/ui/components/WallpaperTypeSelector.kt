package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.wallpapers.WallpaperI

@Composable
internal fun WallpaperTypeSelector(
    supportsDynamicWallpaper: Boolean,
    selectedWallpaperType: WallpaperI.SelectedWallpaperType,
    onClick: (WallpaperI.SelectedWallpaperType) -> Unit,
    modifier: Modifier = Modifier,
    disableNotSelected: Boolean = false
) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        if (supportsDynamicWallpaper) {
            BigChip(
                onClick = {
                    onClick(WallpaperI.SelectedWallpaperType.Dynamic)
                },
                selected = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Dynamic,
                text = WallpaperI.SelectedWallpaperType.Dynamic.label,
                Modifier.weight(1f),
                enabled = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Dynamic || !disableNotSelected
            )
        }
        BigChip(
            onClick = {
                onClick(WallpaperI.SelectedWallpaperType.Static)
            },
            selected = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Static,
            text = WallpaperI.SelectedWallpaperType.Static.label,
            Modifier.weight(1f),
            enabled = selectedWallpaperType == WallpaperI.SelectedWallpaperType.Static || !disableNotSelected
        )
    }
}

@LightDarkPreview
@Composable
private fun DescriptionAndActionsPreview() {
    WallManPreviewTheme {
        var type by remember { mutableStateOf(WallpaperI.SelectedWallpaperType.Static) }
        WallpaperTypeSelector(
            supportsDynamicWallpaper = true,
            selectedWallpaperType = type,
            onClick = {
                type = it
            }
        )
    }
}