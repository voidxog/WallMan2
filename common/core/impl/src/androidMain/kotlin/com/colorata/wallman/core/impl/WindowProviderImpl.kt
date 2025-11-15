package com.voidxog.wallman2.core.impl

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.voidxog.wallman2.core.data.module.WindowProvider

internal class WindowProviderImpl(private val windowSize: WindowSizeClass): WindowProvider {
    override fun isLayoutCompact(): Boolean =
        windowSize.widthSizeClass == WindowWidthSizeClass.Compact

    override fun isLayoutMedium(): Boolean =
        windowSize.widthSizeClass == WindowWidthSizeClass.Medium

    override fun isLayoutExpanded(): Boolean =
        windowSize.widthSizeClass == WindowWidthSizeClass.Expanded
}
