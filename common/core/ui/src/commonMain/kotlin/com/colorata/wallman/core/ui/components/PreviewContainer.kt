package com.voidxog.wallman2.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.isCompositionLaunched
import com.voidxog.wallman2.core.data.animation
import com.voidxog.wallman2.core.ui.LightDarkPreview
import com.voidxog.wallman2.core.ui.modifiers.RotationState
import com.voidxog.wallman2.core.ui.modifiers.detectRotation
import com.voidxog.wallman2.core.ui.modifiers.displayRotation
import com.voidxog.wallman2.core.ui.modifiers.rememberRotationState
import com.voidxog.wallman2.core.ui.theme.WallManPreviewTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PreviewContainer(
    key: Any?,
    modifier: Modifier = Modifier,
    content: @Composable (RotationState) -> Unit
) {
    val borderWidth = remember { Animatable(1f) }
    val animation = MaterialTheme.animation
    val duration = MaterialTheme.animation.durationSpec.extraLong4 * 1.5f
    val compositionLaunched = isCompositionLaunched()
    LaunchedEffect(key) {
        if (!compositionLaunched) return@LaunchedEffect
        launch {
            delay(duration.toLong())
            borderWidth.animateTo(1f, animation.emphasized())
        }
        borderWidth.animateTo(8f, animation.emphasized())
    }
    val state = rememberRotationState()
    Box(
        modifier
            .detectRotation(state)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .displayRotation(state, layer = 0f)
                .clip(MaterialTheme.shapes.extraLarge)
                .updateEffect(key, effectIntensity = 0.05f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .fillMaxSize()
        )
        content(state)
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
                .size(200.dp)
        ) {
            Box(
                Modifier
                    .displayRotation(it, layer = 1f)
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}
