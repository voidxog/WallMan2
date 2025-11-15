package com.voidxog.wallman2.settings.about.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.voidxog.wallman2.core.data.*
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.core.data.module.NavigationController
import com.voidxog.wallman2.settings.about.api.AboutDestination
import com.voidxog.wallman2.settings.memory.api.MemoryDestination
import com.voidxog.wallman2.settings.mirror.api.MirrorDestination
import com.voidxog.wallman2.ui.icons.ContentCopy
import com.voidxog.wallman2.ui.icons.Storage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

fun CoreModule.SettingsViewModel() = SettingsViewModel(navigationController)

class SettingsViewModel(
    private val navigation: NavigationController
) : ViewModel() {
    private val items = persistentListOf(
        /*SettingsItem(
            Strings.animations,
            Strings.animationsDescription,
            Icons.Default.Animation,
            Destinations.AnimationScreen()
        ),*/ // TODO: Add in preview release
        SettingsItem(
            Strings.memoryOptimization,
            Strings.keepYourMemoryFree,
            Icons.Default.Storage,
            Destinations.MemoryDestination()
        ),
        SettingsItem(
            Strings.mirrors,
            Strings.youCanAddOtherMirrorIfCurrentDoesNotWork,
            Icons.Default.ContentCopy,
            Destinations.MirrorDestination()
        ),
        SettingsItem(
            Strings.aboutWallMan,
            Strings.contactInfoDevelopersMore,
            Icons.Default.Info,
            Destinations.AboutDestination()
        )
    )

    data class SettingsItem(
        val name: Polyglot,
        val description: Polyglot,
        val icon: ImageVector,
        val destination: Destination
    )


    private fun onSettingsClick(
        setting: SettingsItem
    ) {
        navigation.navigate(setting.destination)
    }

    val state by lazyMolecule {
        SettingsScreenState(
            items
        ) { event ->
            when (event) {
                is SettingsScreenEvent.GoToSettings -> onSettingsClick(event.setting)
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
}
