package com.colorata.wallman.core.ui.modifiers

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.disabled() = this then GreyScaleModifier { 0f } then alpha(0.4f)

fun Modifier.disabledWhen(condition: Boolean) = composed {
    val saturation by animateFloatAsState(if (condition) 0f else 1f, label = "saturation")
    val alpha by animateFloatAsState(if (condition) 0.4f else 1f, label = "alpha")
    this then GreyScaleModifier { saturation } then graphicsLayer { this.alpha = alpha }
}
private class GreyScaleModifier(private val saturation: () -> Float): DrawModifier {
    override fun ContentDrawScope.draw() {
        val saturationMatrix = ColorMatrix().apply { setToSaturation(saturation()) }
        val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
        val paint = Paint().apply {
            colorFilter = saturationFilter
        }
        drawIntoCanvas {
            it.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
            drawContent()
            it.restore()
        }
    }
}