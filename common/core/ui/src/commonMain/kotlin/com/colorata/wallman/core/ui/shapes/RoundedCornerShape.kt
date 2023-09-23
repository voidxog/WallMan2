package com.colorata.wallman.core.ui.shapes

import androidx.compose.ui.unit.Dp

fun RoundedCornerShapeTopBottom(top: Dp, bottom: Dp = top) =
    androidx.compose.foundation.shape.RoundedCornerShape(
        topStart = top,
        topEnd = top,
        bottomStart = bottom,
        bottomEnd = bottom
    )

fun RoundedCornerShapeStartEnd(start: Dp, end: Dp = start) =
    androidx.compose.foundation.shape.RoundedCornerShape(
        topStart = start,
        topEnd = end,
        bottomStart = start,
        bottomEnd = end
    )
