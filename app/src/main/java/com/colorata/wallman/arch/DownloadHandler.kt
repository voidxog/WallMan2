package com.colorata.wallman.arch

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

interface DownloadHandler {
    suspend fun downloadFile(url: String, path: String, update: (Float) -> Unit = { }): Result<Unit>

    fun downloadFileInBackground(
        url: String,
        path: String,
        description: String
    ): Flow<Result<Unit>>

    fun stopDownloadingFileInBackground(url: String, path: String)
}

object NoopDownloadHandler : DownloadHandler {
    override suspend fun downloadFile(
        url: String,
        path: String,
        update: (Float) -> Unit
    ): Result<Unit> {
        return Result.Success(Unit)
    }

    override fun downloadFileInBackground(
        url: String,
        path: String,
        description: String
    ): Flow<Result<Unit>> {
        return flow { }
    }

    override fun stopDownloadingFileInBackground(url: String, path: String) {}
}

class AndroidDownloadHandler(val context: Context, val scope: CoroutineScope) : DownloadHandler {

    private val mutex = Mutex()
    private val downloadIdsByUrl = mutableMapOf<String, Long>()
    private val downloadManager by lazy { context.getSystemService(DownloadManager::class.java) }
    override suspend fun downloadFile(
        url: String,
        path: String,
        update: (Float) -> Unit
    ): Result<Unit> {
        return NetApi.downloadFile(url, path, update)
    }

    @SuppressLint("Range")
    override fun downloadFileInBackground(
        url: String,
        path: String,
        description: String
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading(0f))
            val downloadFile = File(path)
            withIO {
                if (downloadFile.exists()) downloadFile.delete()
            }

            val request = createDownloadRequest(url, downloadFile, description)
            val id = downloadManager.enqueue(request)
            mutex.withLock {
                downloadIdsByUrl[url] = id
            }
            while (true) {
                watchForDownloadProgress(id, onUpdate = {
                    emit(Result.Loading(it))
                }, onSuccess = {
                    emit(Result.Success(Unit))
                    mutex.withLock {
                        downloadIdsByUrl.remove(url)
                    }
                    return@flow
                }, onError = {
                    emit(Result.Error(Throwable("Cannot download $description")))
                    downloadFile.delete()
                    mutex.withLock {
                        downloadIdsByUrl.remove(url)
                    }
                    return@flow
                })
                mutex.withLock {
                    downloadIdsByUrl.remove(url)
                }
            }
        }
    }

    override fun stopDownloadingFileInBackground(url: String, path: String) {
        scope.launchIO({ it.printStackTrace() }) {
            mutex.withLock {
                if (downloadIdsByUrl.containsKey(url)) {
                    downloadManager.remove(downloadIdsByUrl[url] ?: 0)
                    downloadIdsByUrl.remove(url)
                }
            }
        }
    }

    @SuppressLint("Range")
    private inline fun watchForDownloadProgress(
        id: Long,
        onUpdate: (Float) -> Unit,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        while (true) {
            val query = DownloadManager.Query().apply { setFilterById(id) }
            val cursor = downloadManager.query(query).apply { moveToFirst() }
            runCatching {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    onSuccess()
                } else if (status == DownloadManager.STATUS_FAILED) {
                    onError()
                }
                val bytesDownloaded =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                cursor.close()
                val progress = bytesDownloaded.toFloat() / bytesTotal.toFloat()
                if (progress == 1f) {
                    onSuccess()
                }
                onUpdate(progress)
            }.onFailure {
                onError()
            }
        }
    }

    private fun createDownloadRequest(url: String, downloadFile: File, description: String) =
        DownloadManager.Request(Uri.parse(url)).apply {
            setDescription(Strings.downloading.value)
            setTitle(description)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationUri(Uri.fromFile(downloadFile))
            setMimeType("application/vnd.android.package-archive")
        }
}