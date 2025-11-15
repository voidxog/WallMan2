package com.voidxog.wallman2.widget.api

import com.voidxog.wallman2.core.data.module.CoreModule

interface WidgetModule : CoreModule {
    val widgetRepository: EverydayWidgetRepository
}

