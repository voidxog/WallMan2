import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

projectDependencies(
    androidMainBlock = {
        implementation(Libraries.AndroidX.work)
        implementation(Libraries.Compose.glance)
        implementation(Libraries.Compose.glanceAppWidget)
    }) {
    modules {
        core.data()

        wallpapers.api()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}