package com.colorata.wallman.arch

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import com.colorata.wallman.wallpaper.PackageReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface AppsProvider {
    fun installedApps(): StateFlow<List<String>>

    fun installApp(path: String): Result<Unit>

    fun deleteApp(packageName: String): Result<Unit>
}

object NoopAppsProvider : AppsProvider {
    override fun installedApps(): StateFlow<List<String>> {
        return MutableStateFlow(listOf())
    }

    override fun installApp(path: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override fun deleteApp(packageName: String): Result<Unit> {
        return Result.Success(Unit)
    }
}

class AndroidAppsProvider(private val context: Context) : AppsProvider {
    private var receiver: PackageReceiver? = null
    private val _installedApps by lazy { MutableStateFlow(getInstalledApps()) }

    override fun installedApps(): StateFlow<List<String>> {
        return _installedApps
    }

    override fun installApp(path: String): Result<Unit> {
        return runCatching {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.apply {
                setDataAndType(
                    FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        File(path)
                    ), "application/vnd.android.package-archive"
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.start()
            return@runCatching Result.Success(Unit)
        }.getOrElse { Result.Error(it) }
    }

    override fun deleteApp(packageName: String): Result<Unit> {
        return runCatching {
            val intent = Intent(
                Intent.ACTION_DELETE, Uri.parse("package:$packageName")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.start()
            Result.Success(Unit)
        }.getOrElse { Result.Error(it) }
    }

    fun unloadResources() {
        if (receiver != null) {
            context.unregisterReceiver(receiver)
            receiver = null
        }
    }

    fun loadResources() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED)
        intentFilter.addDataScheme("package")
        receiver = PackageReceiver {
            _installedApps.value = getInstalledApps()
        }
        context.registerReceiver(receiver, intentFilter)
    }

    private fun getInstalledApps(): List<String> {
        return context.applicationContext.packageManager.getInstalledApplications(
            PackageManager.GET_META_DATA
        ).map { it.packageName }
    }

    private fun Intent.start() = context.startActivity(this)
}