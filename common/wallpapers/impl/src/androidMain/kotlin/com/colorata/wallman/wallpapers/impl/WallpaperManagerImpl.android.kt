package com.colorata.wallman.wallpapers.impl

import com.colorata.wallman.core.ApplicationSettings
import com.colorata.wallman.core.AppsProvider
import com.colorata.wallman.core.DownloadHandler
import com.colorata.wallman.core.SystemProvider
import com.colorata.wallman.core.data.Result
import com.colorata.wallman.core.data.mutate
import com.colorata.wallman.core.data.runResulting
import com.colorata.wallman.wallpapers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import java.io.File

actual class WallpaperManagerImpl(
    private val systemProvider: SystemProvider,
    private val appsProvider: AppsProvider,
    private val downloadHandler: DownloadHandler,
    applicationSettings: ApplicationSettings,
    private val wallpaperProvider: WallpaperProvider,
    private val scope: CoroutineScope
): WallpaperManager {
    private val settings = applicationSettings.settings()
    private val wallpaperPacks by lazy { WallpaperPacks.values().toList() }
    private val cacheStorage by lazy { systemProvider.externalCacheDirectoryPath }
    private val _installedWallpaperPacks by lazy {
        appsProvider.installedApps().map { apps ->
            apps.filter { packageName -> wallpaperPacks.any { it.packageName == packageName } }
                .map { packageName -> wallpaperPacks.first { it.packageName == packageName } }
        }.stateIn(scope, SharingStarted.Eagerly, initialValue = listOf())
    }

    private val _cachedWallpaperPacks by lazy {
        MutableStateFlow(getCachedWallpapers())
    }

    override fun installedWallpaperPacks(): StateFlow<List<WallpaperPacks>> {
        return _installedWallpaperPacks
    }

    override fun cachedWallpaperPacks(): StateFlow<List<WallpaperPacks>> {
        return _cachedWallpaperPacks
    }

    override suspend fun installWallpaperPack(pack: WallpaperPacks): Result<Unit> {
        return appsProvider.installApp(path = cacheStorage + "/" + pack.url)
    }

    override suspend fun deleteWallpaperPack(pack: WallpaperPacks): Result<Unit> {
        return appsProvider.deleteApp(packageName = pack.packageName)
    }

    override fun downloadWallpaperPack(pack: WallpaperPacks): Flow<Result<Unit>> {
        return downloadHandler.downloadFileInBackground(
            settings.value.mirror + pack.url,
            cacheStorage + "/" + pack.url,
            pack.description.value
        ).onEach {
            if (it is Result.Success) _cachedWallpaperPacks.value += pack
            else if (it is Result.Error) _cachedWallpaperPacks.value =
                _cachedWallpaperPacks.value.mutate { remove(pack) }
        }
    }

    override fun deleteWallpaperPackCache(pack: WallpaperPacks): Result<Unit> {
        return runResulting {
            File(cacheStorage + "/" + pack.url).delete()
            _cachedWallpaperPacks.value.mutate { remove(pack) }
        }
    }

    override fun stopDownloadingWallpaperPack(pack: WallpaperPacks) {
        downloadHandler.stopDownloadingFileInBackground(
            settings.value.mirror + pack.url,
            cacheStorage + "/" + pack.url,
        )
    }

    override fun installStaticWallpaper(wallpaper: StaticWallpaper): Flow<Result<Unit>> {
        val subPath = wallpaper.fullUrl()
        return flow {
            emit(Result.Loading(0f))
            val localPath = "$cacheStorage/$subPath"
            val downloadResult = downloadHandler.downloadFile(
                settings.value.mirror + subPath,
                localPath
            )
            if (downloadResult is Result.Error) {
                emit(downloadResult)
                return@flow
            }
            wallpaperProvider.installStaticWallpaper(localPath).collect {
                emit(it)
            }
        }
    }

    override fun currentlyInstalledDynamicWallpaper(): Flow<WallpaperProvider.LiveWallpaper?> {
        return wallpaperProvider.currentLiveWallpaper()
    }

    private fun getCachedWallpapers(): List<WallpaperPacks> {
        return File(cacheStorage).listFiles()?.filter { file ->
            wallpaperPacks.any {
                it.url == file.name
            }
        }?.map { file ->
            wallpaperPacks.first { it.url == file.name }
        }?.filter { pack ->
            val fileLength =
                File(cacheStorage + "/" + pack.url).length()
            println("$fileLength ${pack.checksum}")
            fileLength == pack.checksum
        } ?: listOf()
    }
}