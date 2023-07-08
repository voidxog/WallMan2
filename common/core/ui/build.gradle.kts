import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

projectDependencies {
    modules {
        core.data()
    }
    api(Libraries.Colorata.animateAsLifestyle)
    api(Libraries.AndroidX.lifecycleViewModel)
    api(Libraries.AndroidX.lifecycleCompose)
    api(com.colorata.wallman.buildSrc.Libraries.Compose.navigation)
    api(Libraries.Compose.material3WindowSize)
    api(Libraries.Compose.uiUtil)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Accompanist.systemUiController)
    api(com.colorata.wallman.buildSrc.Libraries.Compose.uiToolingPreview)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}