package com.voidxog.wallman2.core.impl

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.voidxog.wallman2.core.data.module.AppSettings
import com.voidxog.wallman2.core.data.module.ApplicationSettings
import com.voidxog.wallman2.core.data.launchIO
import com.voidxog.wallman2.core.data.module.Logger
import com.voidxog.wallman2.core.data.module.throwable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

private val Context.dataStore by dataStore("appSettings.json", AppSettingsSerializer)

@Suppress("BlockingMethodInNonBlockingContext")
private object AppSettingsSerializer : Serializer<AppSettings> {
    override val defaultValue: AppSettings
        get() = AppSettings()

    override suspend fun readFrom(input: InputStream) = kotlin.runCatching {
        Json.decodeFromString(AppSettings.serializer(), input.readBytes().decodeToString())
    }.getOrDefault(AppSettings())

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        output.write(
            Json.encodeToString(AppSettings.serializer(), t).encodeToByteArray()
        )
    }
}

class ApplicationSettingsImpl(
    context: Context,
    private val scope: CoroutineScope,
    private val logger: Logger
) : ApplicationSettings {
    private val androidSettings = context.dataStore
    private val _settings by lazy {
        androidSettings.data.stateIn(scope, SharingStarted.Lazily, AppSettings())
    }

    override fun settings(): StateFlow<AppSettings> {
        return _settings
    }

    override fun mutate(block: (AppSettings) -> AppSettings) {
        scope.launchIO({ logger.throwable(it) }) {
            androidSettings.updateData { block(it) }
        }
    }

}
