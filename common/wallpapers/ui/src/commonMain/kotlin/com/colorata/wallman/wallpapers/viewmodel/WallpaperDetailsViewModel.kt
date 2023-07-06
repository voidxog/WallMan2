package com.colorata.wallman.wallpapers.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.wallpapers.*
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

fun Graph.WallpaperDetailsViewModel(wallpaperHashCode: Int) =
    WallpaperDetailsViewModel(wallpapersRepository, wallpaperHashCode, wallpaperManager, intentHandler)

class WallpaperDetailsViewModel(
    private val repo: WallpapersRepository,
    private val wallpaperHashCode: Int,
    private val wallpaperManager: WallpaperManager,
    private val intentHandler: IntentHandler
) : ViewModel() {

    private val wallpaper: WallpaperI
        get() = repo.wallpapers.first { it.hashCode() == wallpaperHashCode }

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

    init {
        viewModelScope.launchIO({ it.printStackTrace() }) {
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
        viewModelScope.launchIO({ it.printStackTrace() }) {
            wallpaperManager.currentlyInstalledDynamicWallpaper().collect { liveWallpaper ->
                isLiveWallpaperInstalled.value =
                    if (liveWallpaper == null) false else
                        wallpaper.dynamicWallpapers[selectedWallpaperIndex.value]
                            .isSameAs(liveWallpaper)
            }
        }

    }

    private fun onDownloadClick() {
        viewModelScope.launch {
            when (downloadState.value) {
                DynamicWallpaper.DynamicWallpaperCacheState.Cached -> {
                    val result = wallpaperManager.installWallpaperPack(
                        wallpaper.parent
                    )
                    if (result is Result.Error<Unit>) result.throwable.printStackTrace()
                }

                DynamicWallpaper.DynamicWallpaperCacheState.Downloading -> {
                    wallpaperManager.stopDownloadingWallpaperPack(
                        wallpaper.parent
                    )
                    downloadJob?.cancel()
                    downloadJob = null
                    progress.value = 100f
                }

                DynamicWallpaper.DynamicWallpaperCacheState.NotCached -> {
                    downloadJob = launchIO({ it.printStackTrace() }) {
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
        viewModelScope.launchIO({ it.printStackTrace() }) {
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
                                    it.throwable.printStackTrace()
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
        val updatedProgress by progress.collectAsState()
        val selectedType by selectedWallpaperType.collectAsState()
        val action by actionType.collectAsState()
        val selectedWallpaper by selectedBaseWallpaper.collectAsState()
        val wallpaperVariants by wallpaperVariants.collectAsState()
        return@lazyMolecule WallpaperDetailsScreenState(
            wallpaper,
            selectedWallpaper,
            wallpaperVariants,
            updatedProgress,
            cacheState,
            selectedType,
            action
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
        val onEvent: (WallpaperDetailsScreenEvent) -> Unit
    )

    @Immutable
    sealed interface WallpaperDetailsScreenEvent {
        object ClickOnActionButton : WallpaperDetailsScreenEvent
        object ClickOnDownload : WallpaperDetailsScreenEvent

        object GoToMaps : WallpaperDetailsScreenEvent

        data class SelectWallpaperType(val type: WallpaperI.SelectedWallpaperType) :
            WallpaperDetailsScreenEvent

        data class SelectBaseWallpaper(val wallpaper: BaseWallpaper) : WallpaperDetailsScreenEvent
    }
}