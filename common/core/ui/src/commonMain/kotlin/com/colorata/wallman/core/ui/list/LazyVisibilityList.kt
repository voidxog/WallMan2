package com.colorata.wallman.core.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.colorata.animateaslifestyle.Transition
import com.colorata.animateaslifestyle.fade
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.ui.animation.animateVisibility
import com.colorata.wallman.core.ui.theme.emphasizedEnterExit

inline fun <T> LazyListScope.visibilityItemsIndexed(
    items: VisibilityList<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    crossinline transition: @Composable (index: Int, item: T) -> Transition = { _, _ -> fade() },
    crossinline itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit
) {
    itemsIndexed(items, key, contentType) { index, item ->
        Box(Modifier.animateVisibility(items.visible[index], transition(index, item))) {
            itemContent(index, item)
        }
    }
}

inline fun <T> LazyListScope.visibilityItems(
    items: VisibilityList<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline contentType: (item: T) -> Any? = { null },
    crossinline transition: @Composable (item: T) -> Transition = { fade() },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    visibilityItemsIndexed(
        items,
        key = if (key == null) null else { _, item -> key(item) },
        contentType = { _, item -> contentType(item) },
        transition = { _, item -> transition(item) }) { _, item ->
        itemContent(item)
    }
}

inline fun <T> LazyStaggeredGridScope.visibilityItemsIndexed(
    items: VisibilityList<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    noinline span: ((index: Int, item: T) -> StaggeredGridItemSpan)? = null,
    crossinline transition: @Composable (index: Int, item: T) -> Transition = { _, _ -> MaterialTheme.animation.emphasizedEnterExit() },
    crossinline itemContent: @Composable LazyStaggeredGridItemScope.(index: Int, item: T) -> Unit
) {
    itemsIndexed(items, key, contentType, span) { index, item ->
        Box(Modifier.animateVisibility(items.visible[index], transition(index, item))) {
            itemContent(index, item)
        }
    }
}

inline fun <T> LazyStaggeredGridScope.visibilityItems(
    items: VisibilityList<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline contentType: (item: T) -> Any? = { 0 },
    noinline span: ((item: T) -> StaggeredGridItemSpan)? = null,
    crossinline transition: @Composable (item: T) -> Transition = { MaterialTheme.animation.emphasizedEnterExit() },
    crossinline itemContent: @Composable LazyStaggeredGridItemScope.(item: T) -> Unit
) {
    visibilityItemsIndexed(
        items,
        key = if (key == null) null else { _, item -> key(item) },
        contentType = { _, item -> contentType(item) },
        span = if (span == null) null else { _, item -> span(item) },
        transition = { _, item -> transition(item) }) { _, item ->
        itemContent(item)
    }
}

inline fun <T> LazyGridScope.visibilityItemsIndexed(
    items: VisibilityList<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline contentType: (index: Int, item: T) -> Any? = { _, _ -> 0 },
    noinline span: (LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
    crossinline transition: @Composable (index: Int, item: T) -> Transition = { _, _ -> MaterialTheme.animation.emphasizedEnterExit() },
    crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit
) {
    itemsIndexed(items, key, span, contentType) { index, item ->
        Box(Modifier.animateVisibility(items.visible[index], transition(index, item))) {
            itemContent(index, item)
        }
    }
}

inline fun <T> LazyGridScope.visibilityItems(
    items: VisibilityList<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline contentType: (item: T) -> Any? = { null },
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    crossinline transition: @Composable (item: T) -> Transition = { MaterialTheme.animation.emphasizedEnterExit() },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) {
    visibilityItemsIndexed(
        items,
        key = if (key == null) null else { _, item -> key(item) },
        contentType = { _, item -> contentType(item) },
        span = if (span == null) null else { _, item -> span(item) },
        transition = { _, item -> transition(item) }) { _, item ->
        itemContent(item)
    }
}
