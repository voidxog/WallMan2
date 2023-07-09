plugins {
    multiplatformSetup()
    serialization()
    molecule()
}

projectDependencies {
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