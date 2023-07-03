package com.colorata.wallman.viewmodels

import android.os.Build
import android.view.RoundedCorner
import android.view.View
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.repos.MainRepo
import com.colorata.wallman.ui.icons.AutoAwesome
import com.colorata.wallman.ui.icons.Folder
import com.colorata.wallman.ui.screens.CategoriesScreen
import com.colorata.wallman.ui.screens.MainScreen
import com.colorata.wallman.ui.screens.SettingsScreen
import com.colorata.wallman.viewmodels.settings.CacheViewModel
import kotlinx.collections.immutable.persistentListOf
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut

fun Graph.NavigationViewModel() = NavigationViewModel(mainRepo)

class NavigationViewModel(private val repo: MainRepo) : ViewModel() {
    data class NavigationItem(
        val name: String,
        val previewName: Polyglot,
        val filledIcon: ImageVector,
        val outlinedIcon: ImageVector = filledIcon,
        val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { materialFadeThroughIn() },
        val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { materialFadeThroughOut() },
        val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { materialFadeThroughIn() },
        val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { materialFadeThroughOut() },
    )

    val items = persistentListOf(
        NavigationItem(
            name = "Main",
            filledIcon = Icons.Filled.AutoAwesome,
            previewName = Strings.main
        ),
        NavigationItem(
            name = "CategoriesList",
            filledIcon = Icons.Filled.Folder,
            outlinedIcon = Icons.Outlined.Folder,
            previewName = Strings.categories,
            popEnter = {
                materialSharedAxisXIn(false, 100)
            },
            exit = {
                materialSharedAxisXOut(true, 100)
            },
        ),
        NavigationItem(
            name = "More",
            filledIcon = Icons.Filled.Info,
            outlinedIcon = Icons.Outlined.Info,
            previewName = Strings.more,
            popEnter = {
                materialSharedAxisXIn(false, 100)
            },
            exit = {
                materialSharedAxisXOut(true, 100)
            },
        ),
    )

    fun getSystemCorners(view: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) view.rootWindowInsets.getRoundedCorner(
            RoundedCorner.POSITION_TOP_LEFT
        )?.radius ?: 200 else 0
    }

    val wallpapers
        get() = repo.wallpapers

    @Immutable
    sealed interface NavigationScreenEvent {

    }
}