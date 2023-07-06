package com.colorata.wallman.core.data

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.navigation.composable

class MaterialNavGraphBuilder(
    private val builder: NavGraphBuilder,
    val animation: Animation
) {
    @OptIn(ExperimentalAnimationApi::class)
    fun composable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
        exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
        popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
        popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
    ) {
        builder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content
        )
    }
}

fun MaterialNavGraphBuilder.flatComposable(
    destination: Destination,
    hasContinuousChildren: Boolean = false,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        materialFadeThroughIn(durationMillis = animation.durationSpec.medium2)
    },
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        if (hasContinuousChildren) materialSharedAxisXOut(
            forward = true,
            100,
            animation.durationSpec.medium2
        )
        else materialFadeThroughOut(durationMillis = animation.durationSpec.medium2)
    },
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        if (hasContinuousChildren) materialSharedAxisXIn(
            forward = false,
            100,
            animation.durationSpec.medium2
        )
        else materialFadeThroughIn(durationMillis = animation.durationSpec.medium2)
    },
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        materialFadeThroughOut(durationMillis = animation.durationSpec.medium2)
    },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        destination.name,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        content(it)
    }
}

fun MaterialNavGraphBuilder.continuousComposable(
    destination: Destination,
    hasContinuousChildren: Boolean = false,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        materialSharedAxisXIn(forward = true, 100, durationMillis = animation.durationSpec.medium2)
    },
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        if (hasContinuousChildren) materialSharedAxisXOut(
            forward = true,
            100,
            animation.durationSpec.medium2
        )
        else materialFadeThroughOut(durationMillis = animation.durationSpec.medium2)
    },
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        if (hasContinuousChildren) materialSharedAxisXIn(
            forward = false,
            100,
            animation.durationSpec.medium2
        )
        else materialFadeThroughIn(durationMillis = animation.durationSpec.medium2)
    },
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        materialSharedAxisXOut(
            forward = false, 100, durationMillis = animation.durationSpec.medium2
        )
    },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = flatComposable(
    destination,
    hasContinuousChildren,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition,
    content
)