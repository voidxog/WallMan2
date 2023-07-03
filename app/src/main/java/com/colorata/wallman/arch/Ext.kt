package com.colorata.wallman.arch

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import kotlin.coroutines.CoroutineContext

fun Context.isSystemInDarkTheme() =
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }

fun <T> List<T>.mutate(block: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(block)
}

inline fun CoroutineScope.launchWithExceptionHandling(
    crossinline onError: suspend (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit,
    dispatcher: CoroutineContext,
    exceptionDispatcher: CoroutineContext,
): Job {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        launch(exceptionDispatcher) {
            onError(throwable)
        }
    }
    return launch(dispatcher + handler) { block() }
}

inline fun CoroutineScope.launchIO(
    crossinline onError: suspend (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    return launchWithExceptionHandling(
        onError, block, dispatcher = Dispatchers.IO, exceptionDispatcher = Dispatchers.Main
    )
}

inline fun CoroutineScope.launchMain(
    crossinline onError: suspend (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    return launchWithExceptionHandling(
        onError, block, dispatcher = Dispatchers.Main, exceptionDispatcher = Dispatchers.Main
    )
}

suspend inline fun <T> withIO(crossinline block: suspend () -> T) {
    withContext(Dispatchers.IO) { block() }
}

suspend inline fun <T> withMain(crossinline block: suspend () -> T) {
    withContext(Dispatchers.Main) { block() }
}

fun <T> ViewModel.lazyMolecule(content: @Composable () -> T) =
    lazy { viewModelScope.launchMolecule(RecompositionClock.Immediate, content) }

fun <T> CoroutineScope.launchMolecule(content: @Composable () -> T) =
    launchMolecule(RecompositionClock.Immediate, content)

fun <T, R> StateFlow<T>.mapState(
    scope: CoroutineScope, started: SharingStarted = SharingStarted.Lazily, block: (T) -> R
): StateFlow<R> = map { block(it) }.stateIn(scope, started, block(value))

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