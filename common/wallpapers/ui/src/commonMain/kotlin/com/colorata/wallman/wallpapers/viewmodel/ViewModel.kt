package com.colorata.wallman.wallpapers.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.lifecycle.ViewModel
import com.colorata.wallman.core.viewModelWithReceiver
import com.colorata.wallman.wallpapers.WallpapersModule

@Composable
inline fun <reified T : ViewModel> viewModel(noinline block: @DisallowComposableCalls WallpapersModule.() -> T): T {
    return viewModelWithReceiver({ wallpapersModule }, block)
}