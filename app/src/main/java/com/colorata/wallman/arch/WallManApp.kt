package com.colorata.wallman.arch

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager

class WallManApp: Application() {
    val graph by lazy { Graph(this) }
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().build())
    }
}

val Context.graph: Graph
    get() = (applicationContext as WallManApp).graph