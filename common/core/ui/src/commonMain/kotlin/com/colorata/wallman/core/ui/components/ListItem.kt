package com.colorata.wallman.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.rememberString

data class ListItem(
    val name: Polyglot,
    val description: Polyglot,
    val icon: ImageVector
)

@Composable
fun ListItem(item: ListItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    androidx.compose.material3.ListItem(
        headlineContent = {
            androidx.compose.material3.Text(
                rememberString(item.name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        supportingContent = {
            androidx.compose.material3.Text(
                rememberString(item.description),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingContent = {
            Icon(
                imageVector = item.icon, contentDescription = rememberString(string = item.name)
            )
        },
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    )
}