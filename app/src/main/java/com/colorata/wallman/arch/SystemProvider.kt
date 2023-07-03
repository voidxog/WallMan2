package com.colorata.wallman.arch

import android.content.Context
import android.os.Build
import android.os.FileObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

interface SystemProvider {
    fun filesInDirectory(path: String): StateFlow<List<String>>

    val cacheDirectoryPath: String

    val filesDirectoryPath: String

    val externalCacheDirectoryPath: String

    val externalFilesDirectoryPath: String
}

object NoopSystemProvider : SystemProvider {
    override val filesDirectoryPath = ""
    override val cacheDirectoryPath = ""
    override val externalCacheDirectoryPath = ""
    override val externalFilesDirectoryPath = ""

    override fun filesInDirectory(path: String): StateFlow<List<String>> {
        return MutableStateFlow(listOf())
    }
}

class AndroidSystemProvider(
    private val context: Context, private val scope: CoroutineScope
) : SystemProvider {
    private val mutex = Mutex()
    private val watchers = mutableMapOf<String, FileObserver>()

    override val filesDirectoryPath: String by lazy { context.filesDir.absolutePath }
    override val cacheDirectoryPath: String by lazy { context.cacheDir.absolutePath }
    override val externalCacheDirectoryPath by lazy { context.externalCacheDir?.absolutePath ?: "" }
    override val externalFilesDirectoryPath by lazy { context.getExternalFilesDir(null)?.absolutePath ?: "" }

    override fun filesInDirectory(path: String): StateFlow<List<String>> {
        val state = MutableStateFlow(getFilesInDirectory(path))
        scope.launchIO(onError = { it.printStackTrace() }) {
            watchDirectoryForUpdates(path) {
                val files = getFilesInDirectory(path)
                state.value = files + listOf("")
                state.value = files
            }
        }
        return state
    }

    fun unloadResources() {
        watchers.forEach { (_, observer) -> observer.stopWatching() }
    }

    fun loadResources() {
        watchers.forEach { (_, observer) -> observer.stopWatching() }
    }

    private fun getFilesInDirectory(path: String): List<String> {
        return File(path).listFiles()?.map { it.name } ?: listOf()
    }

    private suspend fun watchDirectoryForUpdates(path: String, update: () -> Unit) {
        val observer = createFileObserver(path, update)
        observer.startWatching()
        mutex.withLock {
            watchers[path] = observer
        }
    }

    private fun createFileObserver(path: String, update: () -> Unit): FileObserver {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            object : FileObserver(File(path), CREATE or MODIFY or DELETE) {
                override fun onEvent(event: Int, path: String?) {
                    update()
                }
            } else
            @Suppress("DEPRECATION")
            object : FileObserver(path) {
                override fun onEvent(event: Int, path: String?) {
                    update()
                }
            }
    }
}