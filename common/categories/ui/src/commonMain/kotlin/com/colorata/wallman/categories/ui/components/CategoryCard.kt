package com.colorata.wallman.categories.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.colorata.wallman.categories.api.WallpaperCategory
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.mutate
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.components.PixelatedBadge
import com.colorata.wallman.core.ui.shapes.FlowerShape
import com.colorata.wallman.core.ui.shapes.ScallopShape
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.firstPreviewRes
import com.colorata.wallman.wallpapers.hasBadge
import com.colorata.wallman.wallpapers.ui.components.OptionsBadge
import com.colorata.wallman.wallpapers.unifiedOptions
import com.colorata.wallman.wallpapers.walls
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CategoryCard(
    category: WallpaperCategory,
    wallpapers: ImmutableList<WallpaperI>,
    shapes: ImmutableList<Shape>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val options = remember(wallpapers) { wallpapers.unifiedOptions() }
    Column(
        modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(MaterialTheme.spacing.large)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small,
                Alignment.CenterHorizontally
            )
        ) {
            val roundedShape = MaterialTheme.shapes.large
            val largeShapes = remember { persistentListOf(CircleShape, roundedShape) }
            for (index in 0..2) {
                Image(
                    bitmap = bitmapAsset(wallpapers[index].firstPreviewRes()),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(shapes[index])
                        .height(80.dp)
                        .width(if (shapes[index] in largeShapes) 160.dp else 80.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rememberString(string = category.locale.name),
                style = MaterialTheme.typography.headlineMedium,
            )
            if (options.hasBadge()) {
                OptionsBadge(options, containerColor = MaterialTheme.colorScheme.tertiary)
            }
        }
        Text(
            text = rememberString(string = category.locale.description),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

internal fun generateShapesForCard(shapes: Shapes): ImmutableList<Shape> {
    val roundedShape = shapes.large
    val circle = CircleShape
    val largeShapes = persistentListOf(circle, roundedShape)
    return persistentListOf(
        circle, ScallopShape(), FlowerShape(), roundedShape
    ).shuffled().mutate {
        val taken = take(3)
        if (circle in taken && roundedShape in taken) remove(
            largeShapes.random()
        )
    }.toImmutableList()
}

@LightDarkPreview
@Composable
private fun CategorySample() {
    val shapes = MaterialTheme.shapes
    WallManPreviewTheme {
        CategoryCard(
            category = WallpaperCategory.Garden,
            walls.takeLast(5).toImmutableList(),
            remember { generateShapesForCard(shapes) },
            {})
    }
}