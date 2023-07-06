import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("app.cash.molecule")
}

kotlinDependencies {
    implementation(Libraries.Kotlin.coroutines)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Compose.materialMotionNavigation)
    implementation(Libraries.Kotlin.serialization)
    implementation(Libraries.Compose.material3)
    implementation(Libraries.Compose.runtime)
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
    kotlinOptions.freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.21"
    )
}