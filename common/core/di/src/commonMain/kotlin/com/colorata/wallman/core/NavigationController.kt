package com.colorata.wallman.core

import androidx.navigation.NavHostController
import com.colorata.wallman.core.data.Destination
import kotlinx.coroutines.flow.StateFlow

interface NavigationController {
    fun navigate(destination: Destination)

    fun pop()

    fun resetRootTo(destination: Destination)

    val currentPath: StateFlow<String>

    val composeController: NavHostController

    object NoopNavigationController : NavigationController {
        override fun navigate(destination: Destination) {}

        override fun pop() {}

        override fun resetRootTo(destination: Destination) {}

        override val composeController: NavHostController
            get() = error("NavigationController is not provided")

        override val currentPath: StateFlow<String>
            get() = error("NavigationController is not provided")
    }
}
