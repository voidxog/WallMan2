package com.voidxog.wallman2.core.di

import android.content.Context

interface WallManApp {
    val graph: Graph
}

val Context.graph: Graph
    get() = (applicationContext as WallManApp).graph
