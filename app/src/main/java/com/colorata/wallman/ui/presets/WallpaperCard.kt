package com.colorata.wallman.ui.presets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.material3.GroupedColumn
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.wallpaper.WallpaperI
import com.colorata.wallman.wallpaper.countIcon
import com.colorata.wallman.wallpaper.firstPreviewRes
import com.colorata.wallman.wallpaper.shortName
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf


@Composable
fun WallpaperCard(
    wallpaper: WallpaperI,
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    onClick: () -> Unit = { }
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }) {
        GroupedColumn(
            remember {
                persistentListOf<@Composable () -> Unit>(
                    @Composable {
                        Box(
                            modifier = Modifier
                                .background(Color.Black)
                                .fillMaxWidth()
                                .aspectRatio(1f), contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                painter = painterResource(id = remember(wallpaper) { wallpaper.firstPreviewRes() }),
                                contentDescription = "",
                                modifier = Modifier
                                    .graphicsLayer(scaleY = scale, scaleX = scale)
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )

                            Row(
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.medium)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val countIcon = remember(wallpaper) { wallpaper.countIcon() }
                                val icons = remember(countIcon) {
                                    persistentListOf<ImageVector>().mutate {
                                        if (countIcon != null) it.add(countIcon)
                                    }
                                }
                                if (icons.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                                }
                                icons.forEachIndexed { index, icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(vertical = MaterialTheme.spacing.medium)
                                            .size(16.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Spacer(modifier = Modifier.width(if (index == icons.lastIndex) MaterialTheme.spacing.medium else MaterialTheme.spacing.small))

                                }
                            }
                        }
                    },
                    @Composable {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialTheme.spacing.small)
                                .height(50.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = rememberString(string = remember(wallpaper) { wallpaper.shortName() }),
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        }
                    }
                )
            },
            Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .fillMaxSize()
                .padding(
                    horizontal = MaterialTheme.spacing.large,
                    vertical = MaterialTheme.spacing.medium
                ),
            outerCorner = MaterialTheme.spacing.large,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            it()
        }
    }
}