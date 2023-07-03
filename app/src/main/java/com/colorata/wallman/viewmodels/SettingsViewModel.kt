package com.colorata.wallman.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import com.colorata.animateaslifestyle.material3.isCompact
import com.colorata.wallman.arch.Graph
import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.navigation.Destination
import com.colorata.wallman.repos.MainRepo
import com.colorata.wallman.ui.icons.Animation
import com.colorata.wallman.ui.icons.ContentCopy
import com.colorata.wallman.ui.icons.Storage
import com.colorata.wallman.ui.screens.settings.AboutScreen
import com.colorata.wallman.ui.screens.settings.AnimationsScreen
import com.colorata.wallman.ui.screens.settings.CacheScreen
import com.colorata.wallman.ui.screens.settings.MirrorScreen
import com.colorata.wallman.viewmodels.settings.AboutViewModel
import com.colorata.wallman.viewmodels.settings.AnimationsViewModel
import com.colorata.wallman.viewmodels.settings.CacheViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

fun Graph.SettingsViewModel() = SettingsViewModel(mainRepo)

class SettingsViewModel(
    private val repo: MainRepo
) : ViewModel() {
    val items = persistentListOf(
        SettingsItem(
            Strings.appMotion,
            Strings.makeMotionYourOwn,
            Icons.Default.Animation,
            "Animation"
        ) {
            AnimationsScreen()
        },
        SettingsItem(
            Strings.memoryOptimization,
            Strings.keepYourMemoryFree,
            Icons.Default.Storage,
            "Cache"
        ) {
            CacheScreen()
        },
        SettingsItem(
            Strings.mirrors,
            Strings.youCanAddOtherMirrorIfCurrentDoesNotWork,
            Icons.Default.ContentCopy,
            "Mirror"
        ) { MirrorScreen() },
        SettingsItem(
            Strings.aboutWallMan,
            Strings.contactInfoDevelopersMore,
            Icons.Default.Info,
            "About"
        ) {
            AboutScreen()
        })

    data class SettingsItem(
        val name: Polyglot,
        val description: Polyglot,
        val icon: ImageVector,
        val route: String,
        val composable: @Composable () -> Unit
    )


    private fun onSettingsClick(
        setting: SettingsItem
    ) {
        repo.navController.navigate(setting.route)
    }

    val state by lazy {
        viewModelScope.launchMolecule(RecompositionClock.Immediate) {
            return@launchMolecule SettingsScreenState(
                items
            ) { event ->
                when (event) {
                    is SettingsScreenEvent.GoToSettings -> onSettingsClick(event.setting)
                }
            }
        }
    }

    data class SettingsScreenState(
        val settingsItems: ImmutableList<SettingsItem>,
        val onEvent: (SettingsScreenEvent) -> Unit
    )

    @Immutable
    sealed interface SettingsScreenEvent {
        data class GoToSettings(val setting: SettingsItem) : SettingsScreenEvent
    }

    companion object {
        val SettingsScreenDestination = Destination("More")
    }
}