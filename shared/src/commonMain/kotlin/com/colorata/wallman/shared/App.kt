package com.colorata.wallman.shared

import androidx.compose.runtime.Composable
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.ui.theme.WallManTheme
import com.colorata.wallman.wallpapers.ui.WallpaperDetailsScreen

@Composable
fun App() {
    WallManTheme {
        Navigation()
    }
}