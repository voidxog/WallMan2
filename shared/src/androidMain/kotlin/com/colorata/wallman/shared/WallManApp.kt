package com.voidxog.wallman2.shared

import android.app.Application
import android.content.Context
import com.voidxog.wallman2.core.di.Graph
import com.voidxog.wallman2.core.di.impl.GraphImpl
import com.voidxog.wallman2.widget.impl.EverydayWidgetRepositoryImpl

class WallManApp : Application(), com.voidxog.wallman2.core.di.WallManApp {
    override val graph by lazy { GraphImpl(this) }

    override fun onCreate() {
        super.onCreate()
        graph.widgetModule.widgetRepository = EverydayWidgetRepositoryImpl(currentShapeId = null, this)
        graph.widgetModule.widgetRepository.initializeWorkManager()
    }
}

val Context.graph: Graph
    get() = (applicationContext as WallManApp).graph
