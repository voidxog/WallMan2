plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.di

        +projects.common.wallpapers.api
    }
    namespace = "wallpapers.impl"
}