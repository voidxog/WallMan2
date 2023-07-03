package com.colorata.wallman.wallpaper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PackageReceiver(val update: () -> Unit = { }): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action in mutableListOf(
                Intent.ACTION_PACKAGE_ADDED,
                Intent.ACTION_PACKAGE_REMOVED,
                Intent.ACTION_PACKAGE_REPLACED,
                Intent.ACTION_PACKAGE_FULLY_REMOVED
            ) && p0 != null
        ) {
            update()
        }
    }
}