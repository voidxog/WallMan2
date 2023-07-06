package com.colorata.wallman.wallpapers.impl

import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.wallpapers.walls

actual class WallpapersRepositoryImpl: WallpapersRepository {
    override val wallpapers: List<WallpaperI> by lazy { walls }
}