package com.colorata.wallman.core.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.colorata.wallman.core.data.Animation
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.data.MaterialNavGraphBuilder
import com.colorata.wallman.core.data.module.NavigationController
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationControllerImpl : NavigationController {

    private var onEvent: (Event) -> Unit = {}

    private fun Destination.normalizedPath() = path.replace("//", "/")
    override fun navigate(destination: Destination) {
        onEvent(Event.Navigate(destination))
    }

    override fun pop() {
        onEvent(Event.Pop)
    }

    override fun resetRootTo(destination: Destination) {
        onEvent(Event.ResetRootTo(destination))
    }

    override val currentPath = MutableStateFlow("")

    @Composable
    override fun NavigationHost(
        startDestination: Destination,
        animation: Animation,
        modifier: Modifier,
        builder: MaterialNavGraphBuilder.() -> Unit
    ) {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val route = backStackEntry?.destination?.route
        LaunchedEffect(route) {
            println(route)
            currentPath.emit(route.orEmpty())
        }

        LaunchedEffect(Unit) {
            onEvent = { event ->
                when (event) {
                    Event.Pop -> {
                        navController.navigateUp()
                    }

                    is Event.Navigate -> {
                        navController.navigate(
                            event.destination.normalizedPath()
                        )
                    }

                    is Event.ResetRootTo -> {
                        navController.navigate(
                            event.destination.normalizedPath()
                        ) {
                            popUpTo(event.destination.normalizedPath())
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
        NavHost(navController, startDestination = startDestination.path, modifier = modifier) {
            val navBuilder = MaterialNavGraphBuilder(this, animation)
            navBuilder.builder()
        }
    }

    @Immutable
    private sealed interface Event {
        object Pop : Event

        data class Navigate(val destination: Destination) : Event

        data class ResetRootTo(val destination: Destination) : Event
    }
}