import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

kotlinDependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Wallpapers.api))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}