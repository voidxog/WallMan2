package com.colorata.wallman.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PreviewContainer(key: Any?, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val borderWidth = remember { Animatable(0.1f) }
    val animation = MaterialTheme.animation
    val duration = MaterialTheme.animation.durationSpec.extraLong4 * 3
    LaunchedEffect(key) {
        launch {
            delay(duration.toLong())
            borderWidth.animateTo(0f, animation.emphasized())
        }
        borderWidth.animateTo(4f, animation.emphasized())
    }
    Box(
        modifier.border(
            borderWidth.value.dp,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.shapes.extraLarge
        )
    ) {
        UpdateEffect(
            key,
            Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraLarge)
        )
        content()
    }
}

@LightDarkPreview
@Composable
private fun PreviewContainerPreview() {
    WallManPreviewTheme {
        var key by remember { mutableIntStateOf(0) }
        PreviewContainer(
            key,
            Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    key += 1
                }
                .fillMaxSize()
        ) {

        }
    }
}