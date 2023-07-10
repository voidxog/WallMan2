package com.colorata.wallman.core.data.module

import com.colorata.wallman.core.data.Loadable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SystemProvider: Loadable {
    fun filesInDirectory(path: String): StateFlow<List<String>>

    val cacheDirectoryPath: String

    val filesDirectoryPath: String

    val externalCacheDirectoryPath: String

    val externalFilesDirectoryPath: String

    object NoopSystemProvider : SystemProvider {
        override val filesDirectoryPath = ""
        override val cacheDirectoryPath = ""
        override val externalCacheDirectoryPath = ""
        override val externalFilesDirectoryPath = ""

        override fun filesInDirectory(path: String): StateFlow<List<String>> {
            return MutableStateFlow(listOf())
        }

        override fun load() {}
        override fun unload() {}
    }
}