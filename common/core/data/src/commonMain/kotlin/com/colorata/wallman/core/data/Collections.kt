package com.voidxog.wallman2.core.data

fun <T> List<T>.mutate(block: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(block)
}
