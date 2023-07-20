package com.colorata.wallman.core.ui.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import com.colorata.wallman.core.ui.theme.LocalPaddings

fun Modifier.navigationPadding() = composed {
    padding(com.colorata.wallman.core.ui.modifiers.navigationPadding())
}

@Composable
fun navigationPadding(): PaddingValues = LocalPaddings.current

fun Modifier.navigationStartPadding() = composed {
    padding(start = com.colorata.wallman.core.ui.modifiers.navigationBottomPadding())
}

@Composable
fun navigationStartPadding(): Dp =
    LocalPaddings.current.calculateStartPadding(LocalLayoutDirection.current)


fun Modifier.navigationBottomPadding() = composed {
    padding(bottom = com.colorata.wallman.core.ui.modifiers.navigationBottomPadding())
}

@Composable
fun navigationBottomPadding(): Dp = LocalPaddings.current.calculateBottomPadding()
