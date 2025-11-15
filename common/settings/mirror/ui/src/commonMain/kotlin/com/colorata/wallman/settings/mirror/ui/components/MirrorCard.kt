package com.voidxog.wallman2.settings.mirror.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.sp
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.ui.theme.spacing
import com.voidxog.wallman2.settings.mirror.api.Mirror

@Composable
fun MirrorCard(
    mirror: Mirror, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val animatedBackground by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primaryContainer else
            MaterialTheme.colorScheme.surfaceVariant,
        label = "",
    )
    Column(
        modifier
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() }
            .drawBehind {
                drawRect(animatedBackground)
            }
            .padding(MaterialTheme.spacing.large)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        Text(text = rememberString(mirror.name), fontSize = 20.sp)
        Text(
            text = mirror.url,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}
