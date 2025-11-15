package com.voidxog.wallman2.wallpapers.impl

import com.voidxog.wallman2.wallpapers.WallpaperI
import com.voidxog.wallman2.wallpapers.WallpapersRepository
import com.voidxog.wallman2.wallpapers.walls

class AndroidWallpapersRepositoryImpl: WallpapersRepository {
    override val wallpapers: List<WallpaperI> by lazy { walls }
}
