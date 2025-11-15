package com.voidxog.wallman2.core.data.module

import com.voidxog.wallman2.core.data.AnimationType
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
