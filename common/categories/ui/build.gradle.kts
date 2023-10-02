plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.wallpapers.api
        +projects.common.wallpapers.ui

        +projects.common.categories.api
    }
    namespace = "categories.ui"
}