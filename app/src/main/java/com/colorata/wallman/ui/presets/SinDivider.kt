package com.colorata.wallman.ui.presets

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun SinDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    animateSinMove: Boolean = true,
    frequency: Float = 2f,
    density: Float = 0.1f,
    sinWidth: Dp = 3.dp,
    durationMillis: Int = 30_000,
    @FloatRange(from = 0.0, to = 100.0) progress: Float = 100f
) {
    val offsetAnim by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = animateFloatAsState(targetValue = if (animateSinMove) -100f else 0f).value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing)
        )
    )
    var size by remember { mutableStateOf(IntSize.Zero) }
    val localDensity = LocalDensity.current
    val path by remember(density, frequency, offsetAnim, size) {
        mutableStateOf(Path().apply {
            with(localDensity) {
                var i = 0f
                moveTo(
                    0f,
                    (size.height / 2 + 5.dp.toPx() * sin((i + 1 / density) * PI / 200f * frequency + offsetAnim)).toFloat()
                )
                while (i < size.width * progress / 100f + 1 / density) {
                    val wx = i
                    val wy =
                        ((size.height / 2 + 5f.dp.toPx() * sin((i + 1 / density) * PI / 200f * frequency + offsetAnim))).toFloat()
                    lineTo(wx, wy)
                    i += 1 / density
                }
            }
        })
    }
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .onGloballyPositioned {
                size = it.size
            }
            .height(30.dp)
            .fillMaxWidth()) {
            drawPath(
                path, color = color, style = Stroke(width = sinWidth.toPx(), cap = StrokeCap.Round)
            )

        }
    }
}