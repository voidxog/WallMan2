package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.ui.spacing
import kotlinx.collections.immutable.ImmutableList

@Composable
fun WallpaperVariants(
    wallpapers: ImmutableList<String>,
    selectedWallpaper: String,
    onClick: (wallpaper: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            wallpapers.forEach { wallpaper ->
                Image(
                    bitmap = bitmapAsset(wallpaper),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onClick(wallpaper)
                        }
                        .height(200.dp)
                        .weight(
                            animateFloatAsState(
                                targetValue = if (selectedWallpaper == wallpaper) 5f else 1f,
                                label = "Wallpaper width animation",
                                animationSpec = animation.standard()
                            ).value
                        ),
                    contentScale = ContentScale.Crop)
            }
        }
    }
}
