import com.colorata.wallman.buildSrc.Modules
import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("compose-multiplatform-setup")
}

projectDependencies(androidUnitTestBlock = {}, commonTestBlock = {}) {
    implementation(Libraries.Compose.activity)
    implementation(Libraries.AndroidX.activity)
    implementation(Libraries.AndroidX.splashscreen)
    implementation(Libraries.AndroidX.startup)
    modules {
        core.impl()
        core.data()
        core.ui()
        core.di()

        wallpapers.ui()
        wallpapers.api()

        categories.api()
        categories.ui()

        settings.overview.ui()
        settings.about.ui()
        settings.memory.ui()
        settings.mirror.ui()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

android {
    namespace = App.id
}