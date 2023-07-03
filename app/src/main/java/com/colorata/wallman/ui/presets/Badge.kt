package com.colorata.wallman.ui.presets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Badge(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = "",
        modifier = Modifier
            .padding(vertical = 10.dp)
            .size(16.dp),
        tint = MaterialTheme.colorScheme.onPrimaryContainer
    )
}