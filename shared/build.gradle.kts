import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

kotlinDependencies(androidUnitTestBlock = {}, commonTestBlock = {}) {
    implementation(Libraries.Compose.activity)
    implementation(Libraries.AndroidX.activity)
    implementation(Libraries.AndroidX.splashscreen)
    implementation(Libraries.AndroidX.startup)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Compose.materialMotionNavigation)
    implementation(project(Modules.Core.impl))
    implementation(project(Modules.Core.di))
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Wallpapers.ui))
    implementation(project(Modules.Wallpapers.api))
    implementation(project(Modules.Categories.api))
    implementation(project(Modules.Categories.ui))
    implementation(project(Modules.Settings.Overview.ui))
    implementation(project(Modules.Settings.About.ui))
    implementation(project(Modules.Settings.Mirror.ui))
    implementation(project(Modules.Settings.Memory.ui))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

android {
    namespace = App.id
}