package com.colorata.wallman.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.data.simplifiedLocaleOf
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun Selector(
    values: ImmutableList<Polyglot>,
    selected: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)) {
        values.forEachIndexed { index, item ->
            val isSelected = index == selected
            val weight by animateFloatAsState(
                if (isSelected) 3f else 1f,
                MaterialTheme.animation.emphasized(),
                label = "Weight"
            )
            val containerColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.animation.emphasized(),
                label = "ContainerColor"
            )
            val contentColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                MaterialTheme.animation.emphasized(),
                label = "ContentColor"
            )
            val fontSize by animateFloatAsState(
                if (isSelected) MaterialTheme.typography.displayMedium.fontSize.value else MaterialTheme.typography.bodyLarge.fontSize.value,
                MaterialTheme.animation.emphasized(),
                label = "FontSize"
            )
            Text(rememberString(item),
                Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .clickable { onClick(index) }
                    .drawBehind {
                        drawRect(containerColor)
                    }
                    .padding(MaterialTheme.spacing.large)
                    .weight(weight)
                    .wrapContentSize(Alignment.Center),
                color = contentColor,
                fontSize = fontSize.sp)
        }
    }
}

@LightDarkPreview
@Composable
private fun SelectorPreview() {
    WallManPreviewTheme {
        var selected by remember { mutableIntStateOf(0) }
        Selector(
            persistentListOf("1x1", "2x2", "3x3", "4x4", "5x5").map { simplifiedLocaleOf(it) }
                .toImmutableList(),
            selected = selected,
            onClick = {
                selected = it
            },
            Modifier
                .height(500.dp)
                .fillMaxWidth()
        )
    }
}