plugins {
    id("serialization")
    alias(libs.plugins.configuration)
}

configuration {
    external {
        +libs.kotlin.coroutines
        +libs.kotlin.immutable
        +libs.compose.navigation
        +libs.kotlin.serialization
        +libs.compose.runtime
    }

    internal {
        +libs.compose.material3
    }
    namespace = "core.data"
}