import com.colorata.wallman.buildSrc.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

projectDependencies {
    modules {
        core.data()
        core.di()
        wallpapers.api()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}