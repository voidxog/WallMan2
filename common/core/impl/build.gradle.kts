import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.setup
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(Libraries.Kotlin.coroutines)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Compose.materialMotionNavigation)
    implementation(Libraries.Kotlin.serialization)
    implementation(Libraries.AndroidX.dataStore)
    implementation(Libraries.Ktor.library)
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Wallpapers.impl))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Core.data))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}