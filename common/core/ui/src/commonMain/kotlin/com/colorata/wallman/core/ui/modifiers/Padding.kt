@file:Suppress("UnusedReceiverParameter")

package com.colorata.wallman.core.ui.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import com.colorata.wallman.core.ui.theme.LocalPaddings


object Padding


fun Modifier.navigationPadding() = composed {
    padding(Padding.navigationPadding())
}

@Composable
fun Padding.navigationPadding(): PaddingValues = LocalPaddings.current

fun Modifier.navigationStartPadding() = composed {
    padding(start = Padding.navigationBottomPadding())
}

@Composable
fun Padding.navigationStartPadding(): Dp =
    LocalPaddings.current.calculateStartPadding(LocalLayoutDirection.current)


fun Modifier.navigationBottomPadding() = composed {
    padding(bottom = Padding.navigationBottomPadding())
}

@Composable
fun Padding.navigationBottomPadding(): Dp = LocalPaddings.current.calculateBottomPadding()

@Composable
fun Padding.navigationBarPadding(): Dp =
    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

@Composable
fun Padding.statusBarPadding(): Dp =
    WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()