package com.voidxog.wallman2.settings.memory.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.core.data.bitmapAsset
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.ui.shapes.ScallopShape
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.wallpapers.WallpaperPacks


@OptIn(ExperimentalLayoutApi::class)
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
    Column(
        modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            Image(
                bitmap = bitmapAsset(pack.previewRes),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        ScallopShape()
                    )
            )
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                Text(
                    text = rememberString(pack.previewName),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = size, style = MaterialTheme.typography.bodyMedium)
            }
        }
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

