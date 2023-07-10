package com.colorata.wallman.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colorata.wallman.core.data.module.CoreModule
import com.colorata.wallman.core.di.Graph
import com.colorata.wallman.core.di.LocalGraph


@PublishedApi
@Suppress("UNCHECKED_CAST")
internal class ViewModelFactory(val viewModel: () -> ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel() as T
    }
}

@Composable
inline fun <reified T : ViewModel> viewModel(noinline block: @DisallowComposableCalls CoreModule.() -> T): T {
    return viewModelWithReceiver({ coreModule }, block)
}

@Composable
inline fun <reified T : ViewModel, R> viewModelWithReceiver(
    noinline receiver: @DisallowComposableCalls Graph.() -> R,
    noinline block: @DisallowComposableCalls R.() -> T
): T {
    val graph = LocalGraph.current
    return androidx.lifecycle.viewmodel.compose.viewModel(factory = remember {
        ViewModelFactory {
            graph.receiver().block()
        }
    })
}