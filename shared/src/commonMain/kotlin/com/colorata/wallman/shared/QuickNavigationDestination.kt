package com.voidxog.wallman2.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.voidxog.wallman2.categories.api.CategoriesDestination
import com.voidxog.wallman2.core.data.Destination
import com.voidxog.wallman2.core.data.Destinations
import com.voidxog.wallman2.core.data.Polyglot
import com.voidxog.wallman2.core.data.Strings
import com.voidxog.wallman2.settings.overview.api.SettingsOverviewDestination
import com.voidxog.wallman2.ui.icons.AutoAwesome
import com.voidxog.wallman2.ui.icons.Folder
import com.voidxog.wallman2.wallpapers.MainDestination
import kotlinx.collections.immutable.persistentListOf

data class QuickNavigationDestination(
    val destination: Destination,
    val previewName: Polyglot,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector = filledIcon,
)

internal val quickAccessibleDestinations = persistentListOf(
    QuickNavigationDestination(
        destination = Destinations.MainDestination(),
        filledIcon = Icons.Filled.AutoAwesome,
        previewName = Strings.explore
    ),
    QuickNavigationDestination(
        destination = Destinations.CategoriesDestination(),
        filledIcon = Icons.Filled.Folder,
        outlinedIcon = Icons.Outlined.Folder,
        previewName = Strings.categories
    ),
    QuickNavigationDestination(
        destination = Destinations.SettingsOverviewDestination(),
        filledIcon = Icons.Filled.Info,
        outlinedIcon = Icons.Outlined.Info,
        previewName = Strings.more
    ),
)
