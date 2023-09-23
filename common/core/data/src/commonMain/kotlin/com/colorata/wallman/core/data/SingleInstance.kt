package com.colorata.wallman.core.data

import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@PublishedApi
internal class SingleInstance<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {
    private var ref = WeakReference(initializer())
    private var lock = Any()
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return synchronized(lock) {
            val value = ref.get()
            if (value == null) {
                val initiated = initializer()
                ref = WeakReference(initiated)
                return@synchronized initiated
            }
            value
        }
    }
}

inline fun <T> singleInstance(crossinline initializer: () -> T): ReadOnlyProperty<Any?, T> =
    SingleInstance { initializer() }