package com.colorata.wallman.core.ui.util

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberWindowSize() =
    with(LocalConfiguration.current) {
        remember(screenWidthDp, screenHeightDp) {
            WindowSizeClass.calculateFromSize(
                DpSize(screenWidthDp.dp, screenHeightDp.dp)
            )
        }
    }