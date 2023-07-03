package com.colorata.wallman.wallpaper

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.colorata.wallman.arch.NoopAppsProvider
import com.colorata.wallman.arch.NoopIntentHandler
import com.colorata.wallman.arch.PermissionHandler
import com.colorata.wallman.arch.isSystemInDarkTheme
import com.colorata.wallman.arch.settings.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DayNightReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_CONFIGURATION_CHANGED)
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                context?.dataStore?.data?.stateIn(this)?.value.let {
                    if (context?.isSystemInDarkTheme() != it?.isNightMode && context != null) {
                        // TODO: Move to graph
                        context.dataStore.updateData { data ->
                            data.copy(isNightMode = !(it?.isNightMode ?: false))
                        }
                    }
                }
            }
    }
}

var Context.isDayNightReceiverEnabled: Boolean
    get() {
        return packageManager.getComponentEnabledSetting(
            ComponentName(
                this,
                DayNightReceiver::class.java
            )
        ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    }
    set(value) {
        val component = ComponentName(this, DayNightReceiver::class.java)
        if (value) {
            packageManager.setComponentEnabledSetting(
                component,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        } else {
            packageManager.setComponentEnabledSetting(
                component,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }