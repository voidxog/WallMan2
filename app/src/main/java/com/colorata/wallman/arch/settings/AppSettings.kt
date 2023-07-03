package com.colorata.wallman.arch.settings

import android.content.Context
import com.colorata.animateaslifestyle.AnimationsPerformance
import com.colorata.wallman.wallpaper.DayNightStaticWallpaper
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.map


@kotlinx.serialization.Serializable
data class AppSettings(
    val animationsPerformance: AnimationsPerformance = AnimationsPerformance.Simplified,
    val mirror: String = "https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/",
    val mirrors: ImmutableList<String> =
        persistentListOf("https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/"),
    val currentDayNightWallpaper: DayNightStaticWallpaper? = null,
    val dayNightUrl: String? = null,
    val isNightMode: Boolean? = null
)

fun currentMirror(context: Context): String {
    var mirror = "https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/"
    runCatching {
        context.dataStore.data.map {
            mirror = it.mirror
        }
    }
    return mirror
}