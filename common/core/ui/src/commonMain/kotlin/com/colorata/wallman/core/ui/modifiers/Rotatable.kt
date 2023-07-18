package com.colorata.wallman.core.ui.modifiers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sin

private const val maxAngle = 50f
private const val pi = Math.PI.toFloat()
private val SpringEasing = Easing { x ->
    val c4 = (2 * pi) / 3f

    return@Easing when (x) {
        0f -> 0f
        1f -> 1f
        else -> 2f.pow(-10 * x) * sin((x * 10f - 0.75f) * c4) + 1f
    }
}

fun Modifier.rotatable() = composed {
    val scope = rememberCoroutineScope()

    var angle by remember { mutableStateOf(Offset.Zero) }
    val rotateY = remember { Animatable(0f) }
    val rotateX = remember { Animatable(0f) }
    val animationSpec = tween<Float>(1000, easing = SpringEasing)

    var viewSize by remember { mutableStateOf(Size.Zero) }
    val backToStart = remember {
        {
            scope.launch {
                rotateX.animateTo(0f, animationSpec = animationSpec)
            }
            scope.launch {
                rotateY.animateTo(0f, animationSpec = animationSpec)
            }
        }
    }
    graphicsLayer {
        rotationY = rotateY.value
        rotationX = rotateX.value
    }
        .onGloballyPositioned { coordinates ->
            viewSize = Size(
                width = coordinates.size.width.toFloat(),
                height = coordinates.size.height.toFloat()
            )
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragCancel = {
                    backToStart()
                },
                onDragEnd = {
                    backToStart()
                }
            ) { change, dragAmount ->
                change.consume()
                if (viewSize != Size.Zero) {
                    val newAngle = getRotationAngles(dragAmount, viewSize)

                    val update = Offset(
                        (angle.x + newAngle.x).coerceIn(-maxAngle, maxAngle),
                        (angle.y + newAngle.y).coerceIn(-maxAngle, maxAngle)
                    )

                    angle = update
                    scope.launch {
                        rotateX.animateTo(angle.y)
                    }
                    scope.launch {
                        rotateY.animateTo(-angle.x)
                    }
                }
            }
        }
}

private fun getRotationAngles(
    distance: Offset,
    size: Size
): Offset {
    val acceleration = 3
    val rotationX = (distance.x / size.width) * maxAngle * acceleration
    val rotationY = (distance.y / size.height) * maxAngle * acceleration
    return Offset(rotationX, rotationY)
}