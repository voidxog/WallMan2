package com.colorata.wallman.core.ui.modifiers

import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sin

private const val maxAngle = 50f
private const val pi = Math.PI.toFloat()
private const val maxTranslation = 140f
private val SpringEasing = Easing { x ->
    val c4 = (2 * pi) / 3f

    return@Easing when (x) {
        0f -> 0f
        1f -> 1f
        else -> 2f.pow(-10 * x) * sin((x * 10f - 0.75f) * c4) + 1f
    }
}

@Stable
interface RotationState {
    fun startRotation()

    fun updateAngles(rotation: Offset)

    fun reset()

    val rotation: Offset

    val isRotationInProgress: Boolean
}

private class RotationStateImpl(private val scope: CoroutineScope) : RotationState {
    override val rotation: Offset
        get() = Offset(_rotationX.value, _rotationY.value)

    private val _rotationX = Animatable(0f)
    private val _rotationY = Animatable(0f)

    override var isRotationInProgress: Boolean by mutableStateOf(false)

    override fun startRotation() {
        isRotationInProgress = true
        angle = Offset.Zero
    }

    private var angle = Offset.Zero
    override fun updateAngles(rotation: Offset) {
        val update = Offset(
            (angle.x + rotation.x).coerceIn(-maxAngle, maxAngle),
            (angle.y + rotation.y).coerceIn(-maxAngle, maxAngle)
        )
        angle = update
        scope.launch {
            _rotationX.animateTo(-update.y)
        }
        scope.launch {
            _rotationY.animateTo(update.x)
        }
    }

    override fun reset() {
        val animationSpec = tween<Float>(1000, easing = SpringEasing)
        scope.launch {
            _rotationX.animateTo(0f, animationSpec)
        }
        scope.launch {
            _rotationY.animateTo(0f, animationSpec)
        }
        isRotationInProgress = false
    }
}

@Composable
fun rememberRotationState(): RotationState {
    val scope = rememberCoroutineScope()
    return remember { RotationStateImpl(scope) }
}

fun Modifier.detectRotation(state: RotationState) = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }

    this then onSizeChanged {
        size = it
    }.pointerInput(Unit) {
        detectDragGestures(
            onDragCancel = {
                state.reset()
            },
            onDragEnd = {
                state.reset()
            },
            onDragStart = {
                state.startRotation()
            }
        ) { change, dragAmount ->
            change.consume()
            if (size != IntSize.Zero) {
                val newAngle = getRotationAngles(dragAmount, size)
                state.updateAngles(newAngle)
            }
        }
    }
}

fun Modifier.displayRotation(
    state: RotationState,
    layer: Float = 0f
) = this then runWhen(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    graphicsLayer {
        rotationY = state.rotation.y
        rotationX = state.rotation.x
        translationX = state.rotation.y * maxTranslation / 90f * layer
        translationY = -state.rotation.x * maxTranslation / 90f * layer
    }
}

private fun getRotationAngles(
    distance: Offset,
    size: IntSize
): Offset {
    val acceleration = 3
    val rotationX = (distance.x / size.width) * maxAngle * acceleration
    val rotationY = (distance.y / size.height) * maxAngle * acceleration
    return Offset(rotationX, rotationY)
}