package com.colorata.wallman.core.data.module

import com.colorata.wallman.core.data.AnimationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val mirror: String = "https://github.com/voidxog/WallManAssets/raw/main/",
    val mirrors: ImmutableList<String> =
        persistentListOf("https://github.com/voidxog/WallManAssets/raw/main/"),
    val animationType: AnimationType = AnimationType.Slide
)