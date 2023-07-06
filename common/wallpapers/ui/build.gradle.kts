import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

projectDependencies {
    modules {
        wallpapers.api()
        core.data()
        core.di()
        core.ui()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}