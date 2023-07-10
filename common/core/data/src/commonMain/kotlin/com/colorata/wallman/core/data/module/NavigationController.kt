package com.colorata.wallman.core.data.module

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.colorata.wallman.core.data.Animation
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import kotlinx.coroutines.flow.StateFlow

interface NavigationController {
    fun navigate(destination: Destination)

    fun pop()

    fun resetRootTo(destination: Destination)

    val currentPath: StateFlow<String>

    @Composable
    fun NavigationHost(
        startDestination: Destination,
        animation: Animation,
        modifier: Modifier,
        builder: MaterialNavGraphBuilder.() -> Unit
    )

    object NoopNavigationController : NavigationController {
        override fun navigate(destination: Destination) {}

        override fun pop() {}

        override fun resetRootTo(destination: Destination) {}

        override val currentPath: StateFlow<String>
            get() = error("NavigationController is not provided")

        @Composable
        override fun NavigationHost(
            startDestination: Destination,
            animation: Animation,
            modifier: Modifier,
            builder: MaterialNavGraphBuilder.() -> Unit
        ) {

        }
    }
}
