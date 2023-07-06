package com.colorata.wallman.core.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import java.util.*
import kotlin.reflect.KProperty

interface LocaleCurrent {
    val value: String
}

fun simplifiedLocaleOf(
    english: String, russian: String = english
) = Polyglot(
    english,
    persistentMapOf(
        "en" to english,
        "ru" to russian
    )
)

fun a() {}

fun Polyglot.formatted(vararg args: Any?) = simplifiedLocaleOf(
    languageMap["en"]?.format(*args) ?: default,
    languageMap["ru"]?.format(*args) ?: default
)

@Composable
fun rememberString(string: Polyglot, vararg args: Any?) =
    remember(string, args) { string.value }.format(*args)

@Composable
fun rememberString(string: Polyglot) =
    remember(string) { string.value }


operator fun LocaleCurrent.getValue(thisObj: Any?, property: KProperty<*>) = value

data class Polyglot(
    val default: String,
    val languageMap: ImmutableMap<String, String>
) : LocaleCurrent {
    override val value: String
        get() = languageMap[Locale.getDefault().language] ?: default
}