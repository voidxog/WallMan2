package com.colorata.wallman.core.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import com.colorata.animateaslifestyle.Animatable
import com.colorata.animateaslifestyle.Transition
import com.colorata.animateaslifestyle.fade
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.launchIO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

private data class AnimateVisibilityNodeElement(val visible: Boolean, val transition: Transition) :
    ModifierNodeElement<AnimateVisibilityNode>() {
    override fun create() = AnimateVisibilityNode(visible, transition)

    override fun update(node: AnimateVisibilityNode) {
        node.update(visible, transition)
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "animateVisibility"
        properties["visible"] = visible
        properties["transition"] = transition
    }
}

private class AnimateVisibilityNode(
    private var visible: Boolean,
    private var transition: Transition
) : LayoutModifierNode, Modifier.Node() {

    private val scaleX = Animatable(targetScaleX(visible, transition))
    private val scaleY = Animatable(targetScaleY(visible, transition))
    private val alpha = Animatable(targetAlpha(visible, transition))
    private val slideOffset = Animatable(targetSlideOffset(visible, transition))

    private var animationJob: Job? = null
    fun update(visible: Boolean, transition: Transition) {
        animationJob?.cancel()

        animationJob = coroutineScope.launch {
            launchIO({}) {
                scaleX.animateTo(
                    targetScaleX(visible, transition),
                    transition.scale?.animationSpec ?: tween()
                )
            }
            launchIO({}) {
                scaleY.animateTo(
                    targetScaleY(visible, transition),
                    transition.scale?.animationSpec ?: tween()
                )
            }
            launchIO({}) {
                alpha.animateTo(
                    targetAlpha(visible, transition),
                    transition.fade?.animationSpec ?: tween()
                )
            }
            launchIO({}) {
                slideOffset.animateTo(
                    targetSlideOffset(visible, transition),
                    transition.slide?.animationSpec ?: tween()
                )
            }
        }
        this.visible = visible
        this.transition = transition
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0) {
                if (transition.scale != null) transformOrigin =
                    transition.scale?.transformOrigin ?: TransformOrigin.Center
                scaleX = this@AnimateVisibilityNode.scaleX.value
                scaleY = this@AnimateVisibilityNode.scaleY.value
                alpha = this@AnimateVisibilityNode.alpha.value
                translationX = this@AnimateVisibilityNode.slideOffset.value.x
                translationY = this@AnimateVisibilityNode.slideOffset.value.y
            }
        }
    }
}

private fun targetSlideOffset(visible: Boolean, transition: Transition) =
    if (visible || transition.slide == null) Offset(
        0f, 0f
    ) else transition.slide!!.from

private fun targetScaleX(visible: Boolean, transition: Transition) =
    if (visible || transition.scale == null) 1f else transition.scale!!.from.x

private fun targetScaleY(visible: Boolean, transition: Transition) =
    if (visible || transition.scale == null) 1f else transition.scale!!.from.y

private fun targetAlpha(visible: Boolean, transition: Transition) =
    if (visible || transition.fade == null) 1f else transition.fade!!.from