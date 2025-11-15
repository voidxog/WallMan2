package com.voidxog.wallman2.shared

import androidx.compose.runtime.Composable
import com.voidxog.wallman2.core.data.Destination
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.ProvideBitmapAssetStore
import com.voidxog.wallman2.wallpapers.MainDestination

@Composable
fun App(startDestination: Destination = Destinations.MainDestination()) {
    ProvideBitmapAssetStore {
        Navigation(startDestination)
    }
}
