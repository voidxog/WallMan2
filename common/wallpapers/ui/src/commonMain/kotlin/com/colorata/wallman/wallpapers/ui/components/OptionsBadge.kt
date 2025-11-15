package com.voidxog.wallman2.wallpapers.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.ui.components.PixelatedBadge
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.wallpapers.WallpaperOptions

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
