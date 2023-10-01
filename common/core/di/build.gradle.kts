plugins {
    serialization()
    id("configuration")
}

configuration {
    modules {
        +projects.common.wallpapers.api
        +projects.common.widget.api
        +projects.common.core.data
    }
    namespace = "core.di"
}