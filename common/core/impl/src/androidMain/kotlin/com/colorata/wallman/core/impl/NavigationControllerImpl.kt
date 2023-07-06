package com.colorata.wallman.core.impl

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.colorata.wallman.core.data.Destination
import com.colorata.wallman.core.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import soup.compose.material.motion.navigation.MaterialMotionComposeNavigator

class NavigationControllerImpl(
    private val context: Context,
    scope: CoroutineScope,
    private val navigators: List<Navigator<out NavDestination>> = emptyList()
) : NavigationController {
    @OptIn(ExperimentalAnimationApi::class)
    private fun createMaterialMotionNavController(
        context: Context, vararg navigators: Navigator<out NavDestination>
    ): NavHostController {
        val navigator = MaterialMotionComposeNavigator()
        val controller = NavHostController(context).apply {
            (listOf(ComposeNavigator(), DialogNavigator(), navigator) + navigators).forEach {
                navigatorProvider.addNavigator(it)
            }
        }
        return controller
    }

    override val composeController by lazy { createMaterialMotionNavController(context, *navigators.toTypedArray()) }
    override fun navigate(destination: Destination) {
        composeController.navigate(destination.path.replace("//", "/"))
    }

    override fun pop() {
        composeController.navigateUp()
    }

    override fun resetRootTo(destination: Destination) {
        composeController.navigate(destination.path.replace("//", "/")) {
            popUpTo(destination.name)
            launchSingleTop = true
        }
    }

    override val currentPath: StateFlow<String> =
        composeController.currentBackStackEntryFlow.map { it.destination.route ?: "" }
            .stateIn(scope, SharingStarted.Lazily, "")
}