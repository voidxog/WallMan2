package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.countIcon
import com.colorata.wallman.wallpapers.firstPreviewRes
import com.colorata.wallman.wallpapers.shortName


@Composable
fun WallpaperCard(
    wallpaper: WallpaperI,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Column(
        modifier
            .clip(
                com.colorata.wallman.core.ui.shapes.RoundedCornerShape(
                    bottom = MaterialTheme.spacing.extraSmall,
                    top = MaterialTheme.spacing.large
                )
            )
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .aspectRatio(1f), contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                bitmap = bitmapAsset(remember(wallpaper) { wallpaper.firstPreviewRes() }),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            val countIcon = remember(wallpaper) { wallpaper.countIcon() }
            if (countIcon != null) {
                Icon(
                    imageVector = countIcon,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(MaterialTheme.spacing.medium)
                        .size(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = rememberString(string = remember(wallpaper) { wallpaper.shortName() }),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .clip(
                    com.colorata.wallman.core.ui.shapes.RoundedCornerShape(
                        top = MaterialTheme.spacing.extraSmall,
                        bottom = MaterialTheme.spacing.large
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.small)
                .height(50.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}