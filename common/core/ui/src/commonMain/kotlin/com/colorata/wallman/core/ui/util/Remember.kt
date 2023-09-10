package com.colorata.wallman.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> rememberDerived(block: () -> T): State<T> {
    return remember { derivedStateOf(block) }
}

@Composable
fun <T> rememberDerived(key: Any?, block: () -> T): State<T> {
    return remember(key) { derivedStateOf(block) }
}

@Composable
fun <T> rememberDerived(key1: Any?, key2: Any?, block: () -> T): State<T> {
    return remember(key1, key2) { derivedStateOf(block) }
}

@Composable
fun <T> rememberDerived(key1: Any?, key2: Any?, key3: Any?, block: () -> T): State<T> {
    return remember(key1, key2, key3) { derivedStateOf(block) }
}

@Composable
fun <T> rememberDerived(vararg keys: Any?, block: () -> T): State<T> {
    return remember(*keys) { derivedStateOf(block) }
}

