package com.colorata.wallman.ui.presets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.material3.shapes.ScallopShape
import com.colorata.animateaslifestyle.shapes.ExperimentalShapeApi
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.wallpaper.WallpaperPacks
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment


@OptIn(ExperimentalShapeApi::class, ExperimentalAnimationApi::class)
@Composable
fun CacheCard(
    pack: WallpaperPacks,
    size: String,
    isCacheEnabled: Boolean = false,
    isDeleteEnabled: Boolean = false,
    onClearCache: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Column(Modifier.padding(MaterialTheme.spacing.medium)) {
        Row(Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = pack.previewRes),
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
            mainAxisSpacing = MaterialTheme.spacing.small,
            mainAxisAlignment = MainAxisAlignment.End
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
