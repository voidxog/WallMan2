import com.colorata.wallman.buildSrc.Libraries
import com.colorata.wallman.buildSrc.projectDependencies

plugins {
    multiplatformSetup()
    serialization()
    molecule()
}

projectDependencies {
    api(Libraries.Kotlin.coroutines)
    api(Libraries.Kotlin.collectionsImmutable)
    api(Libraries.Compose.navigation)
    api(Libraries.Kotlin.serialization)
    implementation(Libraries.Compose.material3)
    api(Libraries.Compose.runtime)
}