package com.colorata.wallman.core.ui.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import com.colorata.animateaslifestyle.Transition
import com.colorata.animateaslifestyle.fade
import com.colorata.wallman.core.data.animation
import kotlin.math.roundToInt

fun Modifier.animateVisibility(
    visible: Boolean,
    transition: Transition = fade()
) = composed {
    val scaleX = animateFloatAsState(
        targetValue = if (visible || transition.scale == null) 1f else transition.scale!!.from.x,
        animationSpec = transition.scale?.animationSpec ?: tween(), label = "ScaleX"
    )
    val scaleY = animateFloatAsState(
        targetValue = if (visible || transition.scale == null) 1f else transition.scale!!.from.y,
        animationSpec = transition.scale?.animationSpec ?: tween(), label = "ScaleY"
    )
    val alpha = animateFloatAsState(
        targetValue = if (visible || transition.fade == null) 1f else transition.fade!!.from,
        animationSpec = transition.fade?.animationSpec ?: tween(), label = "Alpha"
    )
    val slideOffset = animateOffsetAsState(
        targetValue = if (visible || transition.slide == null) Offset(
            0f, 0f
        ) else transition.slide!!.from,
        animationSpec = transition.slide?.animationSpec ?: tween(), label = "Slide"
    )
    graphicsLayer {
        if (transition.scale != null) transformOrigin =
            transition.scale?.transformOrigin ?: TransformOrigin.Center
        this.scaleX = scaleX.value
        this.scaleY = scaleY.value
        this.alpha = alpha.value
        translationX = slideOffset.value.x
        translationY = slideOffset.value.y
    }
}

fun Modifier.animateYOffset(offsetY: Float) = composed {
    val slideOffset = animateFloatAsState(
        targetValue = offsetY,
        animationSpec = MaterialTheme.animation.emphasized(), label = "Slide"
    )
    offset { IntOffset(0, slideOffset.value.toInt()) }
}

@Stable
private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())