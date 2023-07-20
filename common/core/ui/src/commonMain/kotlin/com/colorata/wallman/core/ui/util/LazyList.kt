package com.colorata.wallman.core.ui.util

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable

fun LazyStaggeredGridScope.fullLineItem(
    key: Any? = null,
    contentType: Any? = null,
    content: @Composable LazyStaggeredGridItemScope.() -> Unit
) {
    item(
        key = key,
        contentType = contentType,
        span = StaggeredGridItemSpan.FullLine,
        content = content
    )
}