plugins {
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.wallpapers.api
        +projects.common.wallpapers.ui
        +projects.common.settings.memory.api
    }
    namespace = "settings.memory.ui"
}