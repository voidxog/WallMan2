import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import com.colorata.wallman.buildSrc.setup
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlinDependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Wallpapers.impl))
    implementation(project(Modules.Wallpapers.api))

    implementation(Libraries.AndroidX.dataStore)
    implementation(Libraries.Ktor.library)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}