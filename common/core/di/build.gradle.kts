import com.colorata.wallman.buildSrc.*
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
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Core.data))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}