package com.voidxog.wallman2.wallpapers

fun interface CompatibilityChecker {
    fun isCompatible(): Boolean
}

internal fun trueCompatibilityChecker() = CompatibilityChecker { true }
internal fun falseCompatibilityChecker() = CompatibilityChecker { false }
