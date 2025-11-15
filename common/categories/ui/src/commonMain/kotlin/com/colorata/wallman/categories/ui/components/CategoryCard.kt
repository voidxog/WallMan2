package com.voidxog.wallman2.categories.ui.components

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
import com.voidxog.wallman2.categories.api.WallpaperCategory
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.bitmapAsset
import com.voidxog.wallman2.core.data.mutate
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.ui.LightDarkPreview
import com.voidxog.wallman2.core.ui.components.PixelatedBadge
import com.voidxog.wallman2.core.ui.shapes.FlowerShape
import com.voidxog.wallman2.core.ui.shapes.ScallopShape
import com.voidxog.wallman2.core.ui.theme.WallManPreviewTheme
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.firstPreviewRes
import com.voidxog.wallman2.wallpapers.hasBadge
import com.voidxog.wallman2.wallpapers.ui.components.OptionsBadge
import com.voidxog.wallman2.wallpapers.unifiedOptions
import com.voidxog.wallman2.wallpapers.walls
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
