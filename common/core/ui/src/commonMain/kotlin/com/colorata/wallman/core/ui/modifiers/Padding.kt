package com.colorata.wallman.core.ui.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.colorata.wallman.core.ui.theme.LocalPaddings

fun Modifier.navigationPadding() = composed {
    padding(LocalPaddings.current)
}