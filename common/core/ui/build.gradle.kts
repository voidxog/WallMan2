import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

kotlinDependencies {
    api(Libraries.Colorata.animateAsLifestyle)
    api(Libraries.AndroidX.lifecycleViewModel)
    api(Libraries.AndroidX.lifecycleCompose)
    api(Libraries.Compose.materialMotionNavigation)
    api(Libraries.Compose.material3WindowSize)
    api(Libraries.Compose.uiUtil)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Accompanist.systemUiController)
    api(com.colorata.wallman.buildSrc.Libraries.Compose.uiToolingPreview)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}