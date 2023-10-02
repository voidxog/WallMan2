plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.wallpapers.api
        +projects.common.core.data
        +projects.common.core.ui
        +projects.common.categories.api
    }
    namespace = "wallpapers.ui"
}