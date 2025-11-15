package com.voidxog.wallman2.widget.impl

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.voidxog.wallman2.core.data.module.CoreModule
import com.voidxog.wallman2.core.data.launchIO
import com.voidxog.wallman2.core.data.module.throwable
import com.voidxog.wallman2.widget.api.EverydayWidgetRepository
import com.voidxog.wallman2.widget.api.WidgetModule

class WidgetModuleImpl(coreModule: CoreModule) : WidgetModule, CoreModule by coreModule {
    override var widgetRepository: EverydayWidgetRepository = EverydayWidgetRepository.NoopEverydayWidgetRepository
}

fun WidgetModuleImpl.applyActivity(activity: ComponentActivity) {
    val isShapeConfiguration = activity.isWidgetConfiguration()
    if (isShapeConfiguration) activity.lifecycleScope.launchIO({ logger.throwable(it) }) {
        activity.currentWidgetShapeId().collect {
            widgetRepository = EverydayWidgetRepositoryImpl(it, activity).apply { setActivity(activity) }
        }
    }
}
