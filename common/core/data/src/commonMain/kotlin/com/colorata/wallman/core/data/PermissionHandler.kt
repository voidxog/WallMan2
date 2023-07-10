package com.colorata.wallman.core.data

interface PermissionHandler {
    fun requestPermissions(permissions: List<String>, requestCode: Int = 0)

    object NoopPermissionHandler : PermissionHandler {
        override fun requestPermissions(permissions: List<String>, requestCode: Int) {}
    }
}