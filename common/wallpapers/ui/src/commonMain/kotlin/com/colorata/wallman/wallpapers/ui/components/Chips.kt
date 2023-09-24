package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.colorata.wallman.core.data.Strings
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing
import com.colorata.wallman.ui.icons.Storage
import com.colorata.wallman.wallpapers.Chip
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Chips(
    chips: ImmutableList<Chip>,
    onClick: (Chip) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        chips.forEach { chip ->
            AssistChip(onClick = { onClick(chip) }, label = {
                Text(text = rememberString(chip.previewName))
            }, leadingIcon = {
                Icon(imageVector = chip.icon, contentDescription = "")
            })
        }
    }
}

@LightDarkPreview
@Composable
private fun ChipsPreview() {
    WallManPreviewTheme {
        Chips(
            persistentListOf(
                Chip(Strings.size, Icons.Default.Storage),
                Chip(Strings.size, Icons.Default.Storage)
            ), onClick = {})
    }
}