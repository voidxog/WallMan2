package com.colorata.wallman.core.ui.shapes

import androidx.compose.ui.unit.Dp

fun RoundedCornerShape(top: Dp, bottom: Dp = top) =
    androidx.compose.foundation.shape.RoundedCornerShape(
        topStart = top,
        topEnd = top,
        bottomStart = bottom,
        bottomEnd = bottom
    )