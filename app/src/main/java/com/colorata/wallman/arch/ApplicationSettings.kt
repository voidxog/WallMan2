package com.colorata.wallman.arch

import android.content.Context
import com.colorata.wallman.arch.settings.AppSettings
import com.colorata.wallman.arch.settings.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn

interface ApplicationSettings {
    fun settings(): StateFlow<AppSettings>

    fun mutate(block: (AppSettings) -> AppSettings)
}

object NoopApplicationSettings : ApplicationSettings {
    override fun settings(): StateFlow<AppSettings> {
        return MutableStateFlow(AppSettings())
    }

    override fun mutate(block: (AppSettings) -> AppSettings) {

    }
}

class AndroidApplicationSettings(val context: Context, val scope: CoroutineScope) :
    ApplicationSettings {
    private val androidSettings = context.dataStore
    private val _settings by lazy {
        androidSettings.data.stateIn(scope, SharingStarted.Lazily, AppSettings())
    }

    override fun settings(): StateFlow<AppSettings> {
        return _settings
    }

    override fun mutate(block: (AppSettings) -> AppSettings) {
        scope.launchIO({ it.printStackTrace() }) {
            androidSettings.updateData { block(it) }
        }
    }
}