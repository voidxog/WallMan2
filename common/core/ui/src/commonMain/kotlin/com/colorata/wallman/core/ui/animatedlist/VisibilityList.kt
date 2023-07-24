package com.colorata.wallman.core.ui.animatedlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Stable
interface VisibilityList<T> : ImmutableList<T> {
    fun isVisible(index: Int): Boolean
    fun changeVisibility(index: Int, visible: Boolean)
}

context(CoroutineScope)
suspend fun <T> VisibilityList<T>.animateVisibility(
    visible: Boolean,
    startIndex: Int = 0,
    delayBetweenItems: Long = 50
) {
    suspend fun withAsync(block: suspend () -> Unit) {
        launch {
            block()
        }
    }

    suspend fun animate(index: Int, forward: Boolean = true) {
        if (index in indices) {
            withAsync {
                changeVisibility(index, visible)
            }
            withAsync {
                delay(delayBetweenItems)
                animate(if (forward) index + 1 else index - 1, forward)
            }
        }
    }
    if (startIndex > 0) {
        withAsync {
            animate(startIndex, false)
        }
        withAsync {
            animate(startIndex)
        }
    } else {
        animate(startIndex)
    }
}

context(CoroutineScope)
suspend fun <T> VisibilityList<T>.animateVisibilityAsGrid(
    visible: Boolean,
    cells: Int,
    startIndex: Int = 0,
    delayBetweenItems: Long = 50
) {
    suspend fun withAsync(block: suspend () -> Unit) {
        launch {
            block()
        }
    }

    val already = map { false }.toMutableList()
    suspend fun animate(index: Int, forward: Boolean = true) {
        if (index in indices && !already[index]) {
            withAsync {
                changeVisibility(index, visible)
            }
            withAsync {
                val finalIndex = if (forward) index + 1 else index - 1
                if (finalIndex in indices) {
                    delay(delayBetweenItems)
                    animate(finalIndex, forward)
                    already[finalIndex] = true
                }
            }
            withAsync {
                val finalIndex = if (forward) index + cells else index - cells
                if (finalIndex in indices) {
                    delay(delayBetweenItems)
                    animate(finalIndex, forward)
                    already[finalIndex] = true
                }
            }
        }
    }

    if (startIndex > 0) {
        withAsync {
            animate(startIndex, false)
        }
        withAsync {
            animate(startIndex)
        }
    } else {
        animate(startIndex)
    }
}


internal class VisibilityListImpl<T>(
    initial: ImmutableList<T>, visible: Boolean = false
) : VisibilityList<T>, ImmutableList<T> by initial {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val isVisible = initial.map { visible }.toMutableStateList()

    override fun isVisible(index: Int): Boolean {
        return isVisible[index]
    }

    override fun changeVisibility(index: Int, visible: Boolean) {
        isVisible[index] = visible
    }
}

fun <T> AnimatedList(visible: Boolean, vararg values: T): VisibilityList<T> =
    VisibilityListImpl(values.toList().toImmutableList(), visible)

fun <T> Iterable<T>.toAnimatedList(visible: Boolean): VisibilityList<T> =
    VisibilityListImpl(toImmutableList(), visible)

@Composable
fun <T> rememberVisibilityList(
    visible: Boolean = false,
    builder: @DisallowComposableCalls () -> List<T>
): VisibilityList<T> {
    return remember { builder().toAnimatedList(visible) }
}

@Composable
fun <T> VisibilityList<T>.animatedAtLaunch(
    startIndex: Int = 0,
    delayBetweenItems: Long = 50
): VisibilityList<T> {
    LaunchedEffect(Unit) {
        indices.forEach {
            changeVisibility(it, false)
        }
        animateVisibility(
            visible = true,
            startIndex = startIndex,
            delayBetweenItems = delayBetweenItems
        )
    }
    return this
}

@Composable
fun <T> VisibilityList<T>.animatedAsGridAtLaunch(
    cells: Int,
    startIndex: Int = 0,
    delayBetweenItems: Long = 150
): VisibilityList<T> {
    LaunchedEffect(Unit) {
        indices.forEach {
            changeVisibility(it, false)
        }
        animateVisibilityAsGrid(
            visible = true,
            cells = cells,
            startIndex = startIndex,
            delayBetweenItems = delayBetweenItems
        )
    }
    return this
}