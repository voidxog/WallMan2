package com.colorata.wallman.arch

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.colorata.wallman.ui.icons.Bolt
import com.colorata.wallman.ui.icons.Download
import com.colorata.wallman.ui.icons.Error

data class Badge(
    val description: Polyglot,
    val icon: ImageVector
)

object Badges {
    val Download = Badge(
        description = Strings.Badges.download,
        icon = Icons.Default.Download
    )
    val NotInstallable = Badge(
        description = Strings.Badges.notInstallable,
        icon = Icons.Default.Error
    )
    val Dynamic = Badge(
        description = Strings.Badges.dynamic,
        icon = Icons.Default.Bolt
    )
    val UsesX86 = Badge(
        description = Strings.Badges.usesX86,
        icon = Icons.Default.Warning
    )
}