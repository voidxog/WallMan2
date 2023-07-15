package com.colorata.wallman.core.data

class HastMemoizer1<T, R>(
    private val function: (T) -> R
) : (T) -> R {
    private val cache = mutableMapOf<Int, R>()
    override fun invoke(p1: T): R {
        return cache.getOrPut(p1.hashCode()) { function(p1) }
    }
}

class HastMemoizer2<T1, T2, R>(
    private val function: (T1, T2) -> R
) : (T1, T2) -> R {
    private val cache = mutableMapOf<Int, R>()
    override fun invoke(p1: T1, p2: T2): R {
        return cache.getOrPut(p1.hashCode() and p2.hashCode()) { function(p1, p2) }
    }
}

class Memoizer1<T, R>(
    private val function: (T) -> R
) : (T) -> R {
    private val cache = mutableMapOf<T, R>()
    override fun invoke(p1: T): R {
        return cache.getOrPut(p1) { function(p1) }
    }
}

class Memoizer2<T1, T2, R>(
    private val function: (T1, T2) -> R
) : (T1, T2) -> R {
    private val cache = mutableMapOf<Pair<T1, T2>, R>()
    override fun invoke(p1: T1, p2: T2): R {
        return cache.getOrPut(p1 to p2) { function(p1, p2) }
    }
}

fun <T, R> memoizeHash(function: (T) -> R): (T) -> R = HastMemoizer1(function)

fun <T1, T2, R> memoizeHash(function: (T1, T2) -> R): (T1, T2) -> R = HastMemoizer2(function)

fun <T, R> memoize(function: (T) -> R): (T) -> R = Memoizer1(function)

fun <T1, T2, R> memoize(function: (T1, T2) -> R): (T1, T2) -> R = Memoizer2(function)
