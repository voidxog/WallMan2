package com.colorata.wallman.wallpapers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorata.wallman.core.data.Result
import com.colorata.wallman.core.data.launchIO
import com.colorata.wallman.core.data.launchMolecule
import com.colorata.wallman.core.data.lazyMolecule
import com.colorata.wallman.core.data.module.IntentHandler
import com.colorata.wallman.core.data.module.Logger
import com.colorata.wallman.core.data.module.Permission
import com.colorata.wallman.core.data.module.PermissionHandler
import com.colorata.wallman.core.data.module.PermissionPage
import com.colorata.wallman.core.data.module.throwable
import com.colorata.wallman.core.data.onError
import com.colorata.wallman.core.data.onLoading
import com.colorata.wallman.core.data.onSuccess
import com.colorata.wallman.wallpapers.BaseWallpaper
import com.colorata.wallman.wallpapers.DynamicWallpaper
import com.colorata.wallman.wallpapers.StaticWallpaper
import com.colorata.wallman.wallpapers.WallpaperI
import com.colorata.wallman.wallpapers.WallpaperManager
import com.colorata.wallman.wallpapers.WallpapersModule
import com.colorata.wallman.wallpapers.WallpapersRepository
import com.colorata.wallman.wallpapers.canBe
import com.colorata.wallman.wallpapers.firstBaseWallpaper
import com.colorata.wallman.wallpapers.goToLiveWallpaper
import com.colorata.wallman.wallpapers.isSameAs
import com.colorata.wallman.wallpapers.supportsDynamicWallpapers
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

fun WallpapersModule.WallpaperDetailsViewModel(wallpaperHashCode: Int) =
    WallpaperDetailsViewModel(
        wallpapersRepository,
        wallpaperHashCode,
        wallpaperManager,
        intentHandler,
        permissionHandler,
        logger
    )

class WallpaperDetailsViewModel(
    private val repo: WallpapersRepository,
    private val wallpaperHashCode: Int,
    private val wallpaperManager: WallpaperManager,
    private val intentHandler: IntentHandler,
    private val permissionHandler: PermissionHandler,
    private val logger: Logger
) : ViewModel() {

    private val wallpaper: WallpaperI by lazy {
        repo.wallpapers.find { it.hashCode() == wallpaperHashCode } ?: repo.wallpapers.first()
    }

    private val progress = MutableStateFlow(100f)
    private var downloadJob: Job? = null
    private val downloadState =
        MutableStateFlow(DynamicWallpaper.DynamicWallpaperCacheState.NotCached)
    private val selectedWallpaperType = MutableStateFlow(
        if (wallpaper.supportsDynamicWallpapers()) WallpaperI.SelectedWallpaperType.Dynamic
        else WallpaperI.SelectedWallpaperType.Static
    )
    private val actionType = MutableStateFlow<WallpaperI.ActionType>(
        WallpaperI.ActionType.Install(available = false)
    )
    private val isLiveWallpaperInstalled = MutableStateFlow(false)

    private val selectedWallpaperIndex = MutableStateFlow(0)
    private val selectedBaseWallpaper = MutableStateFlow(wallpaper.firstBaseWallpaper())
    private val wallpaperVariants = MutableStateFlow(getDisplayedWallpaperVariants())
    private val showPermissionRequest = MutableStateFlow(false)

    init {
        viewModelScope.launchIO({ logger.throwable(it) }) {
            wallpaperManager.cachedWallpaperPacks()
                .combine(wallpaperManager.installedWallpaperPacks()) { t1, t2 -> t1 to t2 }
                .collect { result ->
                    val cached = wallpaper.parent in result.first
                    val installed = wallpaper.parent in result.second
                    downloadState.emit(
                        if (progress.value != 100f && !cached) DynamicWallpaper.DynamicWallpaperCacheState.Downloading
                        else if (installed) DynamicWallpaper.DynamicWallpaperCacheState.Installed
                        else if (cached) DynamicWallpaper.DynamicWallpaperCacheState.Cached
                        else DynamicWallpaper.DynamicWallpaperCacheState.NotCached
                    )
                }
        }
        viewModelScope.launchIO({ logger.throwable(it) }) {
            wallpaperManager.resultForDownloadWallpaperPack(wallpaper.parent)?.collect { result ->
                result.onLoading {
                    progress.emit(it * 100)
                    downloadState.value = DynamicWallpaper.DynamicWallpaperCacheState.Downloading
                }
                result.onSuccess {
                    progress.emit(100f)
                    downloadState.value = DynamicWallpaper.DynamicWallpaperCacheState.Cached
                }
                result.onError {
                    progress.emit(100f)
                    downloadState.value = DynamicWallpaper.DynamicWallpaperCacheState.NotCached
                }
            }
        }
        viewModelScope.launchMolecule {
            val selectedType by selectedWallpaperType.collectAsState()
            val downloadState by downloadState.collectAsState()
            val isLiveInstalled by isLiveWallpaperInstalled.collectAsState()
            val available =
                (selectedType == WallpaperI.SelectedWallpaperType.Dynamic
                        && downloadState == DynamicWallpaper.DynamicWallpaperCacheState.Installed)
                        || selectedType == WallpaperI.SelectedWallpaperType.Static
            LaunchedEffect(selectedType, isLiveInstalled, available) {
                if (selectedType == WallpaperI.SelectedWallpaperType.Dynamic && isLiveInstalled) actionType.emit(
                    WallpaperI.ActionType.Installed
                ) else actionType.emit(WallpaperI.ActionType.Install(available))
            }
        }
        viewModelScope.launchIO({ logger.throwable(it) }) {
            wallpaperManager.currentlyInstalledDynamicWallpaper().collect { liveWallpaper ->
                isLiveWallpaperInstalled.value =
                    if (liveWallpaper == null || !wallpaper.supportsDynamicWallpapers()) false else
                        wallpaper.dynamicWallpapers[selectedWallpaperIndex.value]
                            .isSameAs(liveWallpaper)
            }
        }

    }

    private fun onDownloadClick() {
        viewModelScope.launchIO({ logger.throwable(it) }) {
            when (downloadState.value) {
                DynamicWallpaper.DynamicWallpaperCacheState.Cached -> {
                    if (permissionHandler.isPermissionGranted(Permission.InstallUnknownApps)) {
                        val result = wallpaperManager.installWallpaperPack(
                            wallpaper.parent
                        )
                        if (result is Result.Error<Unit>) logger.throwable(result.throwable)
                    } else {
                        showPermissionRequest.emit(true)
                    }
                }

                DynamicWallpaper.DynamicWallpaperCacheState.Downloading -> {
                    wallpaperManager.stopDownloadingWallpaperPack(
                        wallpaper.parent
                    )
                    downloadJob?.cancel()
                    downloadJob = null
                    progress.value = 100f
                    downloadState.value = DynamicWallpaper.DynamicWallpaperCacheState.NotCached
                }

                DynamicWallpaper.DynamicWallpaperCacheState.NotCached -> {
                    downloadJob = launchIO({ logger.throwable(it) }) {
                        wallpaperManager.downloadWallpaperPack(
                            wallpaper.parent
                        ).collect {
                            progress.value = when (it) {
                                is Result.Loading -> {
                                    downloadState.value =
                                        DynamicWallpaper.DynamicWallpaperCacheState.Downloading
                                    it.progress * 100f
                                }

                                is Result.Error -> {
                                    downloadState.value =
                                        DynamicWallpaper.DynamicWallpaperCacheState.NotCached
                                    100f
                                }

                                is Result.Success -> {
                                    100f
                                }
                            }
                        }
                    }
                }

                DynamicWallpaper.DynamicWallpaperCacheState.Installed -> {
                    wallpaperManager.deleteWallpaperPack(
                        wallpaper.parent
                    )
                }
            }
        }
    }

    private fun onActionButtonClick() {
        viewModelScope.launchIO({ logger.throwable(it) }) {
            val action = actionType.value
            when {
                action is WallpaperI.ActionType.Install &&
                        selectedWallpaperType.value == WallpaperI.SelectedWallpaperType.Dynamic &&
                        action.available -> {
                    val selectedWallpaper = selectedBaseWallpaper.value
                    require(selectedWallpaper is DynamicWallpaper) { "Selected Wallpaper is not dynamic" }
                    intentHandler.goToLiveWallpaper(selectedWallpaper)
                }

                action is WallpaperI.ActionType.Install &&
                        selectedWallpaperType.value == WallpaperI.SelectedWallpaperType.Static &&
                        action.available -> {
                    val selectedWallpaper = selectedBaseWallpaper.value
                    require(selectedWallpaper is StaticWallpaper) { "Selected Wallpaper is not static" }
                    wallpaperManager.installStaticWallpaper(selectedWallpaper)
                        .collect {
                            actionType.value = when (it) {
                                is Result.Loading -> WallpaperI.ActionType.Installing
                                is Result.Error -> {
                                    logger.throwable(it.throwable)
                                    WallpaperI.ActionType.Error
                                }

                                is Result.Success -> WallpaperI.ActionType.Installed
                            }
                        }
                }
            }
        }
    }

    private fun selectWallpaperType(type: WallpaperI.SelectedWallpaperType) {
        selectedWallpaperType.value = type
        val updatedVariants = getDisplayedWallpaperVariants()
        wallpaperVariants.value = updatedVariants
        val selectedBase = selectedBaseWallpaper.value
        val updatedBaseWallpaper = updatedVariants.find { it.canBe(selectedBase) }
        if (updatedBaseWallpaper != null) selectedBaseWallpaper.value = updatedBaseWallpaper
    }

    private fun getDisplayedWallpaperVariants(): ImmutableList<BaseWallpaper> {
        val update =
            if (selectedWallpaperType.value == WallpaperI.SelectedWallpaperType.Dynamic)
                wallpaper.dynamicWallpapers
            else wallpaper.staticWallpapers
        return if (update.size == 1) persistentListOf()
        else update
    }

    val state by lazyMolecule {
        val cacheState by downloadState.collectAsState()
        val downloadProgress by progress.collectAsState()
        val selectedType by selectedWallpaperType.collectAsState()
        val action by actionType.collectAsState()
        val selectedWallpaper by selectedBaseWallpaper.collectAsState()
        val wallpaperVariants by wallpaperVariants.collectAsState()
        val showPermissionRequest by showPermissionRequest.collectAsState()
        return@lazyMolecule WallpaperDetailsScreenState(
            wallpaper,
            selectedWallpaper,
            wallpaperVariants,
            downloadProgress,
            cacheState,
            selectedType,
            action,
            showPermissionRequest
        ) { event ->
            when (event) {
                WallpaperDetailsScreenEvent.ClickOnActionButton -> onActionButtonClick()
                WallpaperDetailsScreenEvent.ClickOnDownload -> onDownloadClick()
                WallpaperDetailsScreenEvent.GoToMaps -> {
                    val coordinates = selectedBaseWallpaper.value.coordinates
                    if (coordinates != null) intentHandler.goToMaps(coordinates)
                }

                is WallpaperDetailsScreenEvent.SelectWallpaperType -> selectWallpaperType(event.type)

                is WallpaperDetailsScreenEvent.SelectBaseWallpaper -> selectedBaseWallpaper.value =
                    event.wallpaper

                WallpaperDetailsScreenEvent.DismissPermissionRequest ->
                    this.showPermissionRequest.value = false

                WallpaperDetailsScreenEvent.GoToInstallAppsPermissionsPage -> {
                    intentHandler.goToPermissionPage(PermissionPage.InstallUnknownApps)
                    this.showPermissionRequest.value = false
                }
            }
        }
    }

    @Immutable
    data class WallpaperDetailsScreenState(
        val wallpaper: WallpaperI,
        val selectedWallpaper: BaseWallpaper,
        val wallpaperVariants: ImmutableList<BaseWallpaper>,
        val downloadProgress: Float,
        val cacheState: DynamicWallpaper.DynamicWallpaperCacheState,
        val selectedWallpaperType: WallpaperI.SelectedWallpaperType,
        val actionType: WallpaperI.ActionType,
        val showPermissionRequest: Boolean = false,
        val onEvent: (WallpaperDetailsScreenEvent) -> Unit
    )

    @Immutable
    sealed interface WallpaperDetailsScreenEvent {
        data object ClickOnActionButton : WallpaperDetailsScreenEvent
        data object ClickOnDownload : WallpaperDetailsScreenEvent

        data object GoToMaps : WallpaperDetailsScreenEvent

        data class SelectWallpaperType(val type: WallpaperI.SelectedWallpaperType) :
            WallpaperDetailsScreenEvent

        data class SelectBaseWallpaper(val wallpaper: BaseWallpaper) : WallpaperDetailsScreenEvent

        data object DismissPermissionRequest : WallpaperDetailsScreenEvent

        data object GoToInstallAppsPermissionsPage : WallpaperDetailsScreenEvent
    }
}