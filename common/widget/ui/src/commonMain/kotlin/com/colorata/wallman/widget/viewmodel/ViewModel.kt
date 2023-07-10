package com.colorata.wallman.widget.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.colorata.wallman.core.data.viewModelWithReceiver
import com.colorata.wallman.widget.api.LocalWidgetModule
import com.colorata.wallman.widget.api.WidgetModule

@Composable
inline fun <reified T : ViewModel> viewModel(noinline block: WidgetModule.() -> T): T {
    return viewModelWithReceiver(LocalWidgetModule.current, block)
}