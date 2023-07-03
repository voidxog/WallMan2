package com.colorata.wallman.arch.settings

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore by dataStore("appSettings.json", AppSettingsSerializer)

@Suppress("BlockingMethodInNonBlockingContext")
object AppSettingsSerializer : Serializer<AppSettings> {
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