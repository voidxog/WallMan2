package com.voidxog.wallman2.widget.impl

import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.widget.api.R
import com.voidxog.wallman2.widget.api.ShapeConfiguration

val shapes by lazy {
    listOf(
        ShapeConfiguration(
            R.drawable.ic_clever,
            Strings.clever
        ),
        ShapeConfiguration(
            R.drawable.ic_flower,
            Strings.flower
        ),
        ShapeConfiguration(
            R.drawable.ic_scallop,
            Strings.scallop
        ),
        ShapeConfiguration(
            R.drawable.ic_circle,
            Strings.circle
        ),
        ShapeConfiguration(
            R.drawable.ic_square,
            Strings.square
        )
    )
}
