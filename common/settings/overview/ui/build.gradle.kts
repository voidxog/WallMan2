import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

kotlinDependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Settings.Overview.api))
    implementation(project(Modules.Settings.Memory.api))
    implementation(project(Modules.Settings.Mirror.api))
    implementation(project(Modules.Settings.About.api))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}