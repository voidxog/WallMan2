package com.colorata.wallman.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing

data class ListItem(
    val name: Polyglot,
    val description: Polyglot,
    val icon: ImageVector
)

@Composable
fun ListItem(item: ListItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.small
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon, contentDescription = rememberString(string = item.name),
            modifier = Modifier.size(24.dp)
        )
        Column {
            androidx.compose.material3.Text(
                rememberString(item.name),
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.material3.Text(
                rememberString(item.description),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@LightDarkPreview
@Composable
private fun ListItemPreview() {
    WallManPreviewTheme {
        ListItem(
            item = ListItem(
                Strings.aboutWallMan,
                Strings.animationsDescription,
                Icons.Default.Info
            ), onClick = { /*TODO*/ })
    }
}