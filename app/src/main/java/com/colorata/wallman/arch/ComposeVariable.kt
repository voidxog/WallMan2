package com.colorata.wallman.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KProperty

fun <T> composeVariable(
    classValue: T, getter: (T) -> T = {
        it
    }, setter: (T) -> Unit = {

    }
) = SnapshotMutableComposeVariable(classValue, getter, setter)

interface ComposeVariable<out T> {
    val value: T?
}

operator fun <T> ComposeVariable<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value!!

interface MutableComposeVariable<T> : ComposeVariable<T> {
    override var value: T?
    operator fun component1(): T
    operator fun component2(): (T) -> Unit
}

operator fun <T> MutableComposeVariable<T>.setValue(
    thisObj: Any?,
    property: KProperty<*>,
    value: T
) {
    this.value = value
}

@Suppress("UNCHECKED_CAST")
open class SnapshotMutableComposeVariable<T>(
    classValue: T,
    private val getter: (T) -> T,
    private val setter: (T) -> Unit
) : MutableComposeVariable<T> {

    private var _value: T by mutableStateOf(classValue)
    override var value: T? = _value
        get() {
            return field?.let { getter(it) }
        }
        set(value) {
            if (value != null) {
                _value = value
            }
            field = value
            value?.let { setter(it) }
        }

    override fun component1(): T = value!!

    override fun component2(): (T) -> Unit = { value = it }

}
