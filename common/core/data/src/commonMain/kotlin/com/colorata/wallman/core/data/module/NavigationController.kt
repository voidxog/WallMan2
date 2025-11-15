package com.voidxog.wallman2.core.data.module

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.voidxog.wallman2.core.data.Animation
import com.voidxog.wallman2.core.data.Destination
import com.voidxog.wallman2.core.data.MaterialNavGraphBuilder
import kotlinx.coroutines.flow.StateFlow

interface NavigationController {
    fun navigate(destination: Destination)

    fun pop()

    fun resetRootTo(destination: Destination)

    val currentPath: StateFlow<String>


    @Composable
    fun NavigationHost(
        startDestination: Destination,
        animation: Animation,
        modifier: Modifier,
        builder: MaterialNavGraphBuilder.() -> Unit
    )
}

