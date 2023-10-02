plugins {
    id("configuration")
}

configuration {
    internal {
        +libs.compose.activity
        +libs.compose.navigation

        +libs.androidx.activity
        +libs.androidx.splashscreen
        +libs.androidx.startup
    }
    modules {
        +projects.common.core.impl
        +projects.common.core.data
        +projects.common.core.ui
        +projects.common.core.di

        +projects.common.wallpapers.ui
        +projects.common.wallpapers.api

        +projects.common.categories.api
        +projects.common.categories.ui

        +projects.common.widget.api
        +projects.common.widget.impl
        +projects.common.widget.ui

        +projects.common.settings.overview.api

        +projects.common.settings.overview.ui
        +projects.common.settings.about.ui
        +projects.common.settings.animation.ui
        +projects.common.settings.memory.ui
        +projects.common.settings.mirror.ui
    }
    namespace = "shared"
}