package com.colorata.wallman.arch

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager

class WallManApp: Application() {
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().build())
    }
}