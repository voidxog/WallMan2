package com.colorata.wallman.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.persistentMapOf

private var painters by mutableStateOf(persistentMapOf<Int, Painter>())
@Composable
fun cachedResource(@DrawableRes id: Int): Painter {
    val scope = rememberCoroutineScope()
    return if (painters.containsKey(id)) painters[id]!! else painterResource(id).also {
        painters = painters.put(id, it)
    }
}