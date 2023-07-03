package com.colorata.wallman.arch

import android.app.Activity

interface PermissionHandler {
    fun requestPermissions(permissions: List<String>, requestCode: Int = 0)

    object NoopPermissionHandler : PermissionHandler {
        override fun requestPermissions(permissions: List<String>, requestCode: Int) {}
    }
}

fun PermissionHandler.requestPermissions(vararg permissions: String, requestCode: Int = 0) = requestPermissions(permissions.toList(), requestCode)

class AndroidPermissionHandler(val activity: Activity) : PermissionHandler {
    override fun requestPermissions(permissions: List<String>, requestCode: Int) {
        activity.requestPermissions(permissions.toTypedArray(), requestCode)
    }
}