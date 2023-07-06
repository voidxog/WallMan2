import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("app.cash.molecule")
}

kotlinDependencies {
    api(Libraries.Kotlin.coroutines)
    api(Libraries.Kotlin.collectionsImmutable)
    api(Libraries.Compose.materialMotionNavigation)
    api(Libraries.Kotlin.serialization)
    implementation(Libraries.Compose.material3)
    api(Libraries.Compose.runtime)
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
    kotlinOptions.freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.21"
    )
}