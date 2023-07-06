import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

kotlinDependencies {
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Categories.api))
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.material3)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}