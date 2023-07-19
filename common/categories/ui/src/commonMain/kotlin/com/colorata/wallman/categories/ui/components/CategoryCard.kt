package com.colorata.wallman.categories.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.colorata.wallman.categories.api.WallpaperCategory
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.firstPreviewRes
import com.colorata.wallman.wallpapers.walls
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: WallpaperCategory,
    wallpapers: ImmutableList<WallpaperI>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    bitmap = bitmapAsset(wallpapers[i].firstPreviewRes()),
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
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            minLines = 3,
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
    }
}

@LightDarkPreview
@Composable
private fun CategorySample() {
    WallManPreviewTheme {
        CategoryCard(category = WallpaperCategory.Garden, walls, {})
    }
}