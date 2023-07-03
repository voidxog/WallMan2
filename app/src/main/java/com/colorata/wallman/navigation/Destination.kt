package com.colorata.wallman.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Destination(
    val name: String,
    val arguments: ImmutableList<NamedNavArgument> = persistentListOf(),
    val deepLinks: ImmutableList<NavDeepLink> = persistentListOf()
) {
    operator fun plus(other: Destination): Destination {
        return Destination(name + "/" + other.name, other.arguments, other.deepLinks)
    }
}