plugins {
    multiplatformSetup()
    serialization()
    library()
}

configuration {
    dependencies {
        commonMain {
            internal {
                libs.compose.ui
            }
        }
    }
}
projectDependencies(commonTestBlock = {}) {
    external {
        kotlin.coroutines()
        kotlin.collectionsImmutable()
        compose.navigation()
        kotlin.serialization()
        compose.runtime()
    }
    internal {
        compose.material3()
    }
}

androidNamespace("core.data")