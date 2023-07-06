import com.colorata.wallman.buildSrc.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlinDependencies {
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Core.data))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}