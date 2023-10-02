import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun LibraryExtension.setup(project: Project) {
    val config = project.projectConfiguration
    compileSdk = config.compileSdk
    defaultConfig {
        minSdk = config.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    buildFeatures {
        compose = true
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/resources", "src/commonMain/resources")
            assets.srcDirs("src/androidMain/assets", "src/commonMain/assets")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = project.getVersion("compose_compiler")
    }
}

fun Project.setupKotlin() {
    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions.apply {
            jvmTarget = "18"
            composeReports(project.layout.buildDirectory.asFile.get().absolutePath)
            experimentalContextReceivers()
        }
    }
}

fun KotlinCommonOptions.composeReports(path: String) {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$path"
    )
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.9.0"
    )
}

fun KotlinCommonOptions.experimentalContextReceivers() {
    freeCompilerArgs += listOf("-Xcontext-receivers")
}