package com.colorata.wallman.core.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AppsProvider: Loadable {
    fun installedApps(): StateFlow<List<String>>

    fun installApp(path: String): Result<Unit>

    fun deleteApp(packageName: String): Result<Unit>

    object NoopAppsProvider : AppsProvider {
        override fun installedApps(): StateFlow<List<String>> {
            return MutableStateFlow(listOf())
        }

        override fun installApp(path: String): Result<Unit> {
            return Result.Success(Unit)
        }

        override fun deleteApp(packageName: String): Result<Unit> {
            return Result.Success(Unit)
        }

        override fun load() {}

        override fun unload() {}
    }
}