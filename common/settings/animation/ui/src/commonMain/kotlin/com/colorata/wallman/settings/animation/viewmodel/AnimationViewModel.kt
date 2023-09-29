package com.colorata.wallman.settings.animation.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.colorata.wallman.core.data.AnimationType
import com.colorata.wallman.core.data.lazyMolecule
import com.colorata.wallman.core.data.module.ApplicationSettings
import com.colorata.wallman.core.data.module.CoreModule
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map

internal fun CoreModule.AnimationViewModel() = AnimationViewModel(applicationSettings)
internal class AnimationViewModel(
    private val settings: ApplicationSettings
) : ViewModel() {
    private val selectedAnimationType = settings.settings().map { it.animationType }

    private fun selectAnimationType(type: AnimationType) {
        settings.mutate {
            it.copy(animationType = type)
        }
    }

    val state by lazyMolecule {
        val selectedAnimationType by selectedAnimationType.collectAsState(AnimationType.Slide)
        AnimationState(selectedAnimationType, AnimationType.entries.toImmutableList()) { event ->
            when (event) {
                is AnimationEvent.SelectAnimationType -> selectAnimationType(event.type)
            }
        }
    }
}