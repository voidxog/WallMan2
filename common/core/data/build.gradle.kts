plugins {
    multiplatformSetup()
    serialization()
    library()
}

configuration {
    commonMain {
        external {
            +libs.kotlin.coroutines
            +libs.kotlin.immutable
            +libs.compose.navigation
            +libs.kotlin.serialization
            +libs.compose.runtime
        }

        +libs.compose.material3
    }
    namespace = "core.data"
}