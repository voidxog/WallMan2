package com.colorata.wallman.wallpaper

import com.colorata.wallman.arch.Polyglot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WallpaperLocale(
    val name: Polyglot,
    val description: Polyglot,
    val previewName: Polyglot = name,
    val previewNameVariants: ImmutableList<Polyglot> = persistentListOf()
)
