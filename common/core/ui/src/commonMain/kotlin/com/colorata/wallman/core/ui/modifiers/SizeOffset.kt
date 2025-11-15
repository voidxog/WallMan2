package com.voidxog.wallman2.core.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlin.math.roundToInt

fun Modifier.sizeOffset(xMultiplier: Float = 0f, yMultiplier: Float = 1f) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.place(
                (placeable.width * xMultiplier).roundToInt(),
                (placeable.height * yMultiplier).roundToInt()
            )
        }
    }
