package com.colorata.wallman.core.data

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Destination(
    val name: String,
    val path: String = name,
    val arguments: ImmutableList<NamedNavArgument> = persistentListOf(),
    val deepLinks: ImmutableList<NavDeepLink> = persistentListOf()
) {
    operator fun plus(other: Destination): Destination {
        return Destination(name + "/" + other.name, path + "/" + other.path, other.arguments, other.deepLinks)
    }
}

fun destination(
    name: String,
    path: String = name,
    arguments: ImmutableList<NamedNavArgument> = persistentListOf(),
    deepLinks: ImmutableList<NavDeepLink> = persistentListOf()
) = Destination(name, path, arguments, deepLinks)

fun destinationSubPath(
    path: Any?
) = Destination("", path.toString())