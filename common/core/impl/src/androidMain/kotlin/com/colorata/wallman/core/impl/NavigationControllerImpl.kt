package com.colorata.wallman.core.impl

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.colorata.wallman.core.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NavigationControllerImpl(
    context: Context,
    private val scope: CoroutineScope
) : NavigationController {
    private fun createMaterialMotionNavController(
        context: Context, vararg navigators: Navigator<out NavDestination>
    ): NavHostController {
        val navigator = ComposeNavigator()
        val controller = NavHostController(context).apply {
            (listOf(ComposeNavigator(), DialogNavigator(), navigator) + navigators).forEach {
                navigatorProvider.addNavigator(it)
            }
        }
        return controller
    }

    private var composeController = createMaterialMotionNavController(context)
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

    override val currentPath: StateFlow<String>
        get() = composeController.currentBackStackEntryFlow.map { it.destination.route ?: "" }
            .stateIn(scope, SharingStarted.Lazily, "")

    @Composable
    override fun NavigationHost(
        startDestination: Destination,
        animation: Animation,
        modifier: Modifier,
        builder: MaterialNavGraphBuilder.() -> Unit
    ) {
        val navController = rememberNavController()
        LaunchedEffect(Unit) {
            composeController = navController
        }
        NavHost(navController, startDestination = startDestination.path, modifier = modifier) {
            val navBuilder = MaterialNavGraphBuilder(this, animation)
            navBuilder.builder()
        }
    }
}