plugins {
    serialization()
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.di

        +projects.common.wallpapers.impl
        +projects.common.wallpapers.api

        +projects.common.widget.api
        +projects.common.widget.impl
    }

    internal {
        +libs.androidx.datastore
        +libs.ktor
        +libs.compose.material3.windowsize
    }
    namespace = "core.impl"
}