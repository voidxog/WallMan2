package com.colorata.wallman.arch

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.request.prepareGet
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isNotEmpty
import io.ktor.utils.io.core.readBytes
import java.io.File

object NetApi {
    private val client = HttpClient(Android)

    suspend fun downloadFile(url: String, storageUrl: String): Result<Unit> {
        return downloadFile(url, storageUrl) { println("Downloading $it") }
    }

    suspend fun downloadFile(
        url: String,
        storageUrl: String,
        update: (Float) -> Unit
    ): Result<Unit> {
        return runCatching {
            client.downloadLarge(url, storageUrl, update)
            Result.Success(Unit)
        }.onFailure {
            File(storageUrl).delete()
        }.getOrElse { Result.Error(it) }
    }

}

private suspend fun HttpClient.downloadLarge(
    url: String,
    path: String,
    update: (Float) -> Unit = {}
) {
    val file = File(path)
    if (file.exists()) file.delete()
    file.parentFile?.mkdirs()
    file.createNewFile()
    prepareGet(url).execute { response ->
        var pastPercent = 0
        val channel: ByteReadChannel = response.body()
        val totalLength = response.contentLength()?.toFloat() ?: 100f
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
            while (packet.isNotEmpty) {
                val bytes = packet.readBytes()
                file.appendBytes(bytes)
                val fileLength = file.length().toFloat()
                val currentPercent = fileLength / totalLength
                if ((currentPercent * 1000).toInt() != pastPercent) {
                    update(fileLength / totalLength)
                    pastPercent = (currentPercent * 1000).toInt()
                }
            }
        }
    }

    // TODO: Replace with normal implementation to trigger FileObserver
    val tempFile = File("$path.temp")
    tempFile.createNewFile()
    tempFile.delete()
}