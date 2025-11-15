package com.voidxog.wallman2.core.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voidxog.wallman2.core.data.module.Logger
import com.voidxog.wallman2.core.data.module.throwable
import com.voidxog.wallman2.core.data.molecule.GatedFrameClock
import com.voidxog.wallman2.core.data.molecule.RecompositionMode
import com.voidxog.wallman2.core.data.molecule.launchMolecule
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

inline fun CoroutineScope.launchWithExceptionHandling(
    crossinline onError: suspend (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit,
    dispatcher: CoroutineContext,
    exceptionDispatcher: CoroutineContext,
): Job {
    val handler = CoroutineExceptionHandler { _, throwable ->
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

fun <T> ViewModel.lazyMolecule(content: @Composable () -> T): Lazy<StateFlow<T>> =
    lazy {
        viewModelScope.launchMolecule(
            content
        )
    }

fun ViewModel.launchIO(logger: Logger, block: suspend CoroutineScope.() -> Unit) = viewModelScope.launchIO(logger, block)

fun CoroutineScope.launchIO(logger: Logger, block: suspend CoroutineScope.() -> Unit): Job = launchIO({ logger.throwable(it) }, block)

fun <T> CoroutineScope.launchMolecule(content: @Composable () -> T): StateFlow<T> =
    launchMolecule(RecompositionMode.Immediate, Dispatchers.Main, content)

