package com.voidxog.wallman2.settings.animation.viewmodel

import com.voidxog.wallman2.core.data.AnimationType
import kotlinx.collections.immutable.ImmutableList

data class AnimationState(
    val selectedAnimationType: AnimationType,
    val availableTypes: ImmutableList<AnimationType>,
    val onEvent: (AnimationEvent) -> Unit
)

sealed interface AnimationEvent {
    data class SelectAnimationType(val type: AnimationType): AnimationEvent
}
