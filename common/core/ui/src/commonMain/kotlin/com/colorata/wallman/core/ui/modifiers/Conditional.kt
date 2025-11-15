package com.voidxog.wallman2.core.ui.modifiers

import androidx.compose.ui.Modifier

inline fun Modifier.runWhen(condition: Boolean, block: Modifier.() -> Modifier) =
    this.then(if (condition) block() else Modifier)
