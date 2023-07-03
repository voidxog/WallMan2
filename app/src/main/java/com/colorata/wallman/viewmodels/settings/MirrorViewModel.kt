package com.colorata.wallman.viewmodels.settings

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.colorata.wallman.arch.ApplicationSettings
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.lazyMolecule
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.ui.screens.settings.Mirror
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map

fun Graph.MirrorViewModel() = MirrorViewModel(applicationSettings)
class MirrorViewModel(
    private val applicationSettings: ApplicationSettings
) : ViewModel() {
    private val allMirrors: ImmutableList<Mirror> = persistentListOf(
        Mirror(
            Strings.original, "https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/"
        ), Mirror(
            Strings.mirror1,
            "https://shared02.opsone-cloud.ch/index.php/s/CJW7DGrKskNJMm9/download?path=/"
        )
    )
    val settings = applicationSettings.settings()
    private val defaultMirror = allMirrors[0]
    private val selectedMirror = settings.map { settings -> allMirrors.first { it.url == settings.mirror } }

    private fun selectMirror(mirror: Mirror) {
        applicationSettings.mutate { it.copy(mirror = mirror.url) }
    }
    val state by lazyMolecule {
        val selectedMirror by selectedMirror.collectAsState(initial = defaultMirror)
        val mirrors = allMirrors
        return@lazyMolecule MirrorScreenState(mirrors, selectedMirror) { event ->
            when (event) {
                is MirrorScreenEvent.SelectMirror -> selectMirror(event.mirror)
            }
        }
    }

    data class MirrorScreenState(
        val mirrors: ImmutableList<Mirror>,
        val selectedMirror: Mirror,
        val onEvent: (MirrorScreenEvent) -> Unit
    )

    @Immutable
    sealed interface MirrorScreenEvent {
        data class SelectMirror(val mirror: Mirror) : MirrorScreenEvent
    }

    companion object {
        val MirrorScreenDestination = Destination("Mirror")
    }
}