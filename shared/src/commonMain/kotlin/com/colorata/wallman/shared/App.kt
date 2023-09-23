package com.colorata.wallman.shared

import androidx.compose.runtime.Composable
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.Destinations
import com.colorata.wallman.core.data.ProvideBitmapAssetStore
import com.colorata.wallman.wallpapers.MainDestination

@Composable
fun App(startDestination: Destination = Destinations.MainDestination()) {
    ProvideBitmapAssetStore {
        Navigation(startDestination)
    }
}