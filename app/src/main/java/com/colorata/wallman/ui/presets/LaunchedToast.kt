package com.colorata.wallman.ui.presets

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

/**
 * Makes toast every time key changes. Should be used for debugging only
 * @param key Key that can change
 * @param message message that will be appearing when key is changed
 */
@Composable
fun LaunchedToast(key: Any, message: String) {
    val context = LocalContext.current
    LaunchedEffect(key1 = key) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}