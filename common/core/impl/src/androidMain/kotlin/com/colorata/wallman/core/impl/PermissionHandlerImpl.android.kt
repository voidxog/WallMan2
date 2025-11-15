package com.voidxog.wallman2.core.impl

import android.app.Activity
import android.content.pm.PackageManager
import com.voidxog.wallman2.core.data.module.Permission
import com.voidxog.wallman2.core.data.module.PermissionHandler

class PermissionHandlerImpl(private val activity: Activity) : PermissionHandler {
    override fun requestPermissions(permissions: List<String>, requestCode: Int) {
        activity.requestPermissions(permissions.toTypedArray(), requestCode)
    }

    override fun isPermissionGranted(permission: Permission): Boolean {
        return permission.isGranted()
    }

    private fun Permission.isGranted() = when (this) {
        Permission.InstallUnknownApps -> activity.packageManager.canRequestPackageInstalls()
    }
}
