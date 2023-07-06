package com.colorata.wallman.core.impl

import android.app.Activity
import com.colorata.wallman.core.PermissionHandler

class PermissionHandlerImpl(private val activity: Activity) : PermissionHandler {
    override fun requestPermissions(permissions: List<String>, requestCode: Int) {
        activity.requestPermissions(permissions.toTypedArray(), requestCode)
    }
}