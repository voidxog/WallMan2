import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.kotlinDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

kotlinDependencies {
    api(Libraries.Colorata.animateAsLifestyle)
    implementation(Libraries.AndroidX.lifecycleViewModel)
    implementation(Libraries.Compose.materialMotionNavigation)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Accompanist.systemUiController)
    api(com.colorata.wallman.buildSrc.Libraries.Compose.uiToolingPreview)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}