import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

projectDependencies {
    modules {
        core.data()
        core.di()

        wallpapers.impl()
        wallpapers.api()
    }

    implementation(Libraries.AndroidX.dataStore)
    implementation(Libraries.Ktor.library)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}