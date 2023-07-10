package com.colorata.wallman.widget.api

import com.colorata.wallman.core.data.CoreModule

interface WidgetModule: CoreModule {
    val widgetRepository: EverydayWidgetRepository
}