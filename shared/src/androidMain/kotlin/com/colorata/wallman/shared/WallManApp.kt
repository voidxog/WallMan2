package com.colorata.wallman.shared

import android.app.Application
import android.content.Context
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.core.di.impl.GraphImpl

class WallManApp: Application() {
    val graph by lazy { GraphImpl(this) }
}

val Context.graph: Graph
    get() = (applicationContext as WallManApp).graph