plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
    }
    external {
        +libs.colorata.animations

        +libs.androidx.lifecycle.compose
        +libs.androidx.lifecycle.viewmodel

        +libs.compose.navigation
        +libs.compose.material3
        +libs.compose.material3.windowsize
        +libs.compose.ui.util
        +libs.compose.ui.tooling
        +libs.compose.ui.tooling.preview
    }

    internal {
        +libs.kotlin.immutable
        +libs.compose.systemui
        +libs.compose.material3.colors
    }
    namespace = "core.ui"
}