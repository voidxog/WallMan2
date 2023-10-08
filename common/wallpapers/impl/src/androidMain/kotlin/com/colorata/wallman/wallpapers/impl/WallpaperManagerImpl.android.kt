package com.colorata.wallman.wallpapers.impl

import com.colorata.wallman.core.data.module.ApplicationSettings
import com.colorata.wallman.core.data.module.AppsProvider
import com.colorata.wallman.core.data.module.DownloadHandler
import com.colorata.wallman.core.data.module.SystemProvider
import com.colorata.wallman.core.data.Result
import com.colorata.wallman.core.data.map
import com.colorata.wallman.core.data.mapLoading
import com.colorata.wallman.core.data.mutate
import com.colorata.wallman.core.data.runResulting
import com.colorata.wallman.wallpapers.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

actual class WallpaperManagerImpl(
    private val systemProvider: SystemProvider,
    private val appsProvider: AppsProvider,
    private val downloadHandler: DownloadHandler,
    applicationSettings: ApplicationSettings,
    private val wallpaperProvider: WallpaperProvider,
    private val scope: CoroutineScope
) : WallpaperManager {
    private val settings = applicationSettings.settings()
    private val wallpaperPacks by lazy { WallpaperPacks.entries }
    private val cacheStorage by lazy { systemProvider.externalCacheDirectoryPath }
    private val _installedWallpaperPacks by lazy {
        appsProvider.installedApps().map { apps ->
            apps.filter { packageName -> wallpaperPacks.any { it.packageName == packageName } }
                .map { packageName -> wallpaperPacks.first { it.packageName == packageName } }
        }.stateIn(scope, SharingStarted.Eagerly, initialValue = listOf())
    }

    private val _downloadingWallpaperPacks by lazy { mutableMapOf<WallpaperPacks, Flow<Result<Unit>>>() }

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
        return _downloadingWallpaperPacks.getOrPut(pack) {
            downloadHandler.downloadFileInBackground(
                settings.value.mirror + pack.url,
                cacheStorage + "/" + pack.url,
                pack.description.value
            ).onEach { result ->
                if (result !is Result.Loading) _downloadingWallpaperPacks.remove(pack)
                if (result is Result.Success) _cachedWallpaperPacks.value += pack
                else if (result is Result.Error) _cachedWallpaperPacks.update {
                    it.mutate { remove(pack) }
                }
            }.stateIn(scope, SharingStarted.Lazily, Result.Loading(0f))
        }
    }

    override fun resultForDownloadWallpaperPack(pack: WallpaperPacks): Flow<Result<Unit>>? {
        return _downloadingWallpaperPacks[pack]
    }

    override fun deleteWallpaperPackCache(pack: WallpaperPacks): Result<Unit> {
        return runResulting {
            File(cacheStorage + "/" + pack.url).delete()
            _cachedWallpaperPacks.update { it.mutate { remove(pack) } }
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
        return channelFlow {
            send(Result.Loading(0f))
            val localPath = "$cacheStorage/$subPath"
            val downloadResult = downloadHandler.downloadFile(
                settings.value.mirror + subPath,
                localPath
            ) {
                launch {
                    send(Result.Loading(it / 3f))
                }
            }
            if (downloadResult is Result.Error) {
                send(downloadResult)
                return@channelFlow
            }
            wallpaperProvider.installStaticWallpaper(localPath).collect { result ->
                send(when (result) {
                    is Result.Loading -> result.mapLoading { 0.3f + 2 * result.progress / 3f }
                    else -> result
                })
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
            fileLength == pack.checksum
        } ?: listOf()
    }
}