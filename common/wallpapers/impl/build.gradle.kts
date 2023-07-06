import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libraries.Kotlin.coroutines)
                implementation(Libraries.Kotlin.collectionsImmutable)
                implementation(Libraries.AndroidX.core)
                implementation(Libraries.Compose.runtime)
                implementation(Libraries.Compose.ui)
                implementation(Libraries.Compose.material3)
                implementation(project(Modules.Core.data))
                implementation(project(Modules.Core.di))
                implementation(project(Modules.Wallpapers.api))
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}