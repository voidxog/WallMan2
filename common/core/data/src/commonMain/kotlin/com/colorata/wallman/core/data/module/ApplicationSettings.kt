package com.voidxog.wallman2.core.data.module

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ApplicationSettings {
    fun settings(): StateFlow<AppSettings>

    fun mutate(block: (AppSettings) -> AppSettings)

    object NoopApplicationSettings : ApplicationSettings {
        override fun settings(): StateFlow<AppSettings> {
            return MutableStateFlow(AppSettings())
        }

        override fun mutate(block: (AppSettings) -> AppSettings) {

        }
    }
}
