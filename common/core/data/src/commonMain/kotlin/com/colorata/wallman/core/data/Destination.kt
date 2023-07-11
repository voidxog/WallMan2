package com.colorata.wallman.core.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Destination(
    val name: String,
    val path: String = name,
    val arguments: ImmutableList<DestinationArgument> = persistentListOf()
) {
    operator fun plus(other: Destination): Destination {
        return Destination(
            name + "/" + other.name,
            path + "/" + other.path,
            other.arguments
        )
    }
}

data class DestinationArgument(
    val name: String,
    val defaultValue: String
)

fun destinationArgument(
    name: String,
    defaultValue: String
) = DestinationArgument(name, defaultValue)

fun destination(
    name: String,
    path: String = name,
    arguments: ImmutableList<DestinationArgument> = persistentListOf()
) = Destination(name, path, arguments)

fun destinationSubPath(
    path: Any?
) = Destination("", path.toString())