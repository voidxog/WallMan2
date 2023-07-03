package com.colorata.wallman.ui.presets

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.staggerListOf
import com.colorata.animateaslifestyle.stagger.toStaggerList
import com.colorata.wallman.ui.cachedResource
import com.colorata.wallman.ui.theme.animation
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.wallpaper.BaseWallpaper
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay

@Composable
fun WallpaperVariants(
    wallpapers: ImmutableList<Int>,
    @DrawableRes selectedWallpaper: Int,
    onClick: (wallpaper: Int) -> Unit,
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
                    painter = cachedResource(id = wallpaper),
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
