package com.colorata.wallman.shared

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.colorata.wallman.core.data.*
import com.colorata.wallman.ui.icons.AutoAwesome
import com.colorata.wallman.ui.icons.Folder
import kotlinx.collections.immutable.persistentListOf

data class QuickNavigationDestination(
    val name: String,
    val previewName: Polyglot,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector = filledIcon,
    val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { materialFadeThroughIn() },
    val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { materialFadeThroughOut() },
    val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { materialFadeThroughIn() },
    val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { materialFadeThroughOut() },
)

internal val quickAccessibleDestinations = persistentListOf(
    QuickNavigationDestination(
        name = "Main",
        filledIcon = Icons.Filled.AutoAwesome,
        previewName = Strings.main
    ),
    QuickNavigationDestination(
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
    QuickNavigationDestination(
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