package com.colorata.wallman.ui.presets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.ui.LightDarkPreview
import com.colorata.wallman.ui.theme.WallManPreviewTheme
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.wallpaper.WallpaperCategory
import com.colorata.wallman.wallpaper.categoryWallpapers
import com.colorata.wallman.wallpaper.firstPreviewRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: WallpaperCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wallpapers = remember(category) { category.categoryWallpapers() }
    Card(
        onClick = onClick,
        modifier = modifier, shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {
            for (i in 0..2) {
                Image(
                    painter = painterResource(id = wallpapers[i].firstPreviewRes()),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(if (i == 0) 2f else 1f)
                        .height(80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            }
        }
        androidx.compose.material3.Text(
            text = rememberString(string = category.locale.name),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.small
            )
        )
        androidx.compose.material3.Text(
            text = rememberString(string = category.locale.description),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
    }
}

@LightDarkPreview
@Composable
private fun CategorySample() {
    WallManPreviewTheme {
        CategoryCard(category = WallpaperCategory.Garden, {})
    }
}