import com.colorata.wallman.buildSrc.*
import com.colorata.wallman.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("multiplatform-setup")
}

kotlinDependencies {
    implementation(project(Modules.Core.data))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}