package com.colorata.wallman.viewmodels.settings

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.animateaslifestyle.AnimationsPerformance
import com.colorata.wallman.arch.ApplicationSettings
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.lazyMolecule
import com.colorata.wallman.arch.mapState
import com.colorata.wallman.arch.settings.dataStore
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.repos.MainRepo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

fun Graph.AnimationsViewModel() = AnimationsViewModel(mainRepo, applicationSettings)

class AnimationsViewModel(
    private val repo: MainRepo,
    private val applicationSettings: ApplicationSettings,
) : ViewModel() {
    data class AnimationMode(
        val name: Polyglot,
        val description: Polyglot,
        val performance: AnimationsPerformance,
        val url: String
    )

    private val animationModes = persistentListOf(
        AnimationMode(
            Strings.full,
            Strings.fullAnimationExperience,
            AnimationsPerformance.Full,
            "https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/Internal/animations_full.jpg"
        ), AnimationMode(
            Strings.simplified,
            Strings.someAnimationsWereSimplified,
            AnimationsPerformance.Simplified,
            "https://sam.nl.tab.digital/s/wqZaeixFAsDEdGe/download?path=/Internal/animations_simplified.jpg"
        )
    )

    private val settings = applicationSettings.settings()

    private val selectedAnimationMode =
        settings.mapState(viewModelScope) { animationModes.first { mode -> mode.performance == it.animationsPerformance } }

    private fun changeAnimationMode(mode: AnimationMode) {
        applicationSettings.mutate { it.copy(animationsPerformance = mode.performance) }
    }

    val state by lazyMolecule {
        val selectedAnimationMode by selectedAnimationMode.collectAsState()
        return@lazyMolecule AnimationsScreenState(
            animationModes,
            selectedAnimationMode
        ) { event ->
            when (event) {
                is AnimationsScreenEvent.ClickOnAnimationMode -> changeAnimationMode(event.mode)
            }
        }
    }

    data class AnimationsScreenState(
        val animationModes: ImmutableList<AnimationMode>,
        val currentAnimationMode: AnimationMode?,
        val onEvent: (AnimationsScreenEvent) -> Unit
    )

    @Immutable
    sealed interface AnimationsScreenEvent {
        data class ClickOnAnimationMode(val mode: AnimationMode) : AnimationsScreenEvent
    }

    companion object {
        val AnimationsScreenDestination = Destination("Animation")
    }
}