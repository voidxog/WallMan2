import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

kotlinDependencies {
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Core.ui))
    implementation(Libraries.Colorata.animateAsLifestyle)
    implementation(Libraries.AndroidX.lifecycleCompose)
    implementation(Libraries.AndroidX.lifecycle)
    implementation(Libraries.AndroidX.lifecycleViewModel)
    implementation(Libraries.Compose.materialMotionNavigation)
    implementation(Libraries.Compose.material3WindowSize)
    implementation(Libraries.Compose.uiUtil)
    implementation(Libraries.Kotlin.collectionsImmutable)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}