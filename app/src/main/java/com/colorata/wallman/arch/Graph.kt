package com.colorata.wallman.arch

import android.app.Application
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.colorata.wallman.repos.MainRepo
import com.colorata.wallman.repos.MainRepoImpl
import com.colorata.wallman.wallpaper.WallpaperManager
import com.colorata.wallman.wallpaper.WallpaperManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import soup.compose.material.motion.navigation.MaterialMotionComposeNavigator

class Graph(private val app: Application) {
    val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    var permissionHandler: PermissionHandler = PermissionHandler.NoopPermissionHandler
    var intentHandler: IntentHandler = NoopIntentHandler
    var systemProvider: SystemProvider = NoopSystemProvider
    var appsProvider: AppsProvider = NoopAppsProvider
    var downloadHandler: DownloadHandler = NoopDownloadHandler
    val wallpaperProvider: WallpaperProvider by lazy {
        AndroidWallpaperProvider(app, coroutineScope)
    }
    val applicationSettings: ApplicationSettings by lazy {
        AndroidApplicationSettings(app, coroutineScope)
    }

    val navController by lazy { createMaterialMotionNavController(app) }

    val mainRepo: MainRepo by lazy { MainRepoImpl(app, navController) }

    val wallpaperManager: WallpaperManager by lazy {
        WallpaperManagerImpl(
            systemProvider,
            appsProvider,
            downloadHandler,
            applicationSettings,
            wallpaperProvider,
            coroutineScope
        )
    }
}

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

val LocalGraph = compositionLocalOf<Graph> { error("No Graph provided") }

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(val viewModel: () -> ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel() as T
    }
}

@Composable
inline fun <reified T : ViewModel> viewModel(noinline block: @DisallowComposableCalls Graph.() -> T): T {
    val graph = LocalGraph.current
    return androidx.lifecycle.viewmodel.compose.viewModel(factory = remember { ViewModelFactory { graph.block() } })
}