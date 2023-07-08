import com.colorata.wallman.buildSrc.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

projectDependencies {
    modules {
        wallpapers.api()

        widget.api()
        core.data()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}