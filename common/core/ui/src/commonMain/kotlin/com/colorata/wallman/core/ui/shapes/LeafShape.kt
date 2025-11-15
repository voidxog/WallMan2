package com.voidxog.wallman2.core.ui.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import com.voidxog.wallman2.core.ui.theme.Spacing


fun LeafShape(
    spacing: Spacing,
    continuousStart: Boolean = false,
    continuousEnd: Boolean = false
) = RoundedCornerShape(
    topEnd = spacing.extraSmall,
    topStart = if (continuousStart) spacing.extraSmall else spacing.extraLarge,
    bottomStart = spacing.extraSmall,
    bottomEnd = if (continuousEnd) spacing.extraSmall else spacing.extraLarge
)
