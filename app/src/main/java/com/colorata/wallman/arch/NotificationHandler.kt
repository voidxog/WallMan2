package com.colorata.wallman.arch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.colorata.wallman.R

interface NotificationHandler {
    enum class NotificationChannel(val previewName: Polyglot) {
        Download(Strings.downloadManager),
        Error(Strings.errors)
    }

    data class Notification(
        val title: String,
        val description: String,
        val channel: NotificationChannel,
        val progress: Int? = null
    )

    fun pushNotification(notification: Notification)
}

fun NotificationHandler.NotificationChannel.asAndroidChannel(): NotificationChannel {
    return NotificationChannel(
        previewName.value,
        previewName.value,
        NotificationManager.IMPORTANCE_LOW
    )
}

fun NotificationHandler.Notification.asAndroidNotification(context: Context): Notification {
    return Notification.Builder(context, channel.previewName.value).apply {
        setContentTitle(title)
        setContentText(description)
        setSmallIcon(R.drawable.ic_monochrome)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
        }
        if (progress != null) setProgress(100, progress, false)
    }.build()
}