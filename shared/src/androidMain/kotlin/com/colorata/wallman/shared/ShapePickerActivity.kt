package com.voidxog.wallman2.shared

import androidx.compose.runtime.Composable
import com.voidxog.wallman2.core.di.LocalGraph
import com.voidxog.wallman2.widget.ui.ShapePickerScreen

class ShapePickerActivity: GraphActivity() {
    @Composable
    override fun Content() {
        val graph = LocalGraph.current
        with(graph.widgetModule) {
            ShapePickerScreen()
        }
    }
}
