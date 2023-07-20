package com.colorata.wallman.settings.memory.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.material3.shapes.ScallopShape
import com.colorata.animateaslifestyle.shapes.ExperimentalShapeApi
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.spacing
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.wallpapers.WallpaperPacks


@OptIn(ExperimentalShapeApi::class, ExperimentalLayoutApi::class)
@Composable
fun CacheCard(
    pack: WallpaperPacks,
    size: String,
    modifier: Modifier = Modifier,
    isCacheEnabled: Boolean = false,
    isDeleteEnabled: Boolean = false,
    onClearCache: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Column(modifier.padding(MaterialTheme.spacing.medium)) {
        Row(Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    bitmap = bitmapAsset(pack.previewRes),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(
                            ScallopShape()
                        )
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
            Column {
                Text(
                    text = rememberString(pack.previewName),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(text = size, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        FlowRow(
            Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small, Alignment.End)
        ) {
            OutlinedButton(
                onClick = onClearCache,
                enabled = isCacheEnabled
            ) {
                Icon(Icons.Outlined.Delete, "")
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(text = rememberString(string = Strings.clearCache))
            }
            Button(
                onClick = onDelete,
                enabled = isDeleteEnabled
            ) {
                Icon(Icons.Default.Delete, "")
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(text = rememberString(string = Strings.remove))
            }
        }
    }
}
