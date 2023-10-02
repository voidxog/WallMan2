plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.wallpapers.api

        +projects.common.settings.overview.api
        +projects.common.settings.memory.api
        +projects.common.settings.mirror.api
        +projects.common.settings.about.api
        +projects.common.settings.animation.api
    }
    namespace = "settings.overview.ui"
}