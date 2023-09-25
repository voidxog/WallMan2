package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.components.PixelatedBadge
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.wallpapers.WallpaperOptions

@Composable
fun OptionsBadge(
    options: WallpaperOptions,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer
) {
    PixelatedBadge(
        text = rememberString(
            string = when {
                options.isNew -> Strings.new
                else -> Strings.retro
            }
        ),
        modifier = modifier,
        containerColor = containerColor
    )
}