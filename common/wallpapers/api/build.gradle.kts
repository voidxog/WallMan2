plugins {
    id("configuration")
    id("serialization")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.categories.api
    }
    internal {
        +libs.compose.runtime
        +libs.compose.material3
    }
    namespace = "wallpapers.api"
}