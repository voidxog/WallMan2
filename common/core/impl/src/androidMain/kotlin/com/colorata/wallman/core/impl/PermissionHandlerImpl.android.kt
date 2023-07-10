package com.colorata.wallman.core.impl

import android.app.Activity
import android.content.pm.PackageManager
import com.colorata.wallman.core.data.module.PermissionHandler

class PermissionHandlerImpl(private val activity: Activity) : PermissionHandler {
    override fun requestPermissions(permissions: List<String>, requestCode: Int) {
        activity.requestPermissions(permissions.toTypedArray(), requestCode)
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}