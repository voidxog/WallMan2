package com.voidxog.wallman2.widget.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voidxog.wallman2.core.data.module.IntentHandler
import com.voidxog.wallman2.core.data.launchIO
import com.voidxog.wallman2.core.data.lazyMolecule
import com.voidxog.wallman2.core.data.module.Logger
import com.voidxog.wallman2.core.data.module.throwable
import com.voidxog.wallman2.widget.api.EverydayWidgetRepository
import com.voidxog.wallman2.widget.api.ShapeConfiguration
import com.voidxog.wallman2.widget.api.WidgetModule
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun WidgetModule.ShapePickerViewModel() =
    ShapePickerViewModel(widgetRepository, intentHandler, logger)

class ShapePickerViewModel(
    private val repository: EverydayWidgetRepository,
    private val intentHandler: IntentHandler,
    private val logger: Logger
) : ViewModel() {

    val state by lazyMolecule {
        return@lazyMolecule ScreenState(
            repository.shapes.toImmutableList(),
            repository.currentShape() ?: repository.shapes[0]
        ) { event ->
            when (event) {
                is ScreenEvent.ClickOnShape -> {
                    viewModelScope.launchIO({ logger.throwable(it) }) {
                        repository.updateShape(event.shape)
                        intentHandler.exit()
                    }
                }
            }
        }
    }

    @Immutable
    data class ScreenState(
        val shapes: ImmutableList<ShapeConfiguration>,
        val selectedShape: ShapeConfiguration,
        val onEvent: (ScreenEvent) -> Unit
    )

    @Immutable
    sealed interface ScreenEvent {
        data class ClickOnShape(val shape: ShapeConfiguration) : ScreenEvent
    }
}
