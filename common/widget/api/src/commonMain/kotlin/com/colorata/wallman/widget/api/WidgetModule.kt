package com.colorata.wallman.widget.api

import androidx.compose.runtime.compositionLocalOf
import com.colorata.wallman.core.data.module.CoreModule

interface WidgetModule : CoreModule {
    val widgetRepository: EverydayWidgetRepository
}

val LocalWidgetModule = compositionLocalOf<WidgetModule> { error("WidgetModule is not provided") }