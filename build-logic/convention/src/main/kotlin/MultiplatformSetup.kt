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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
            jvmTarget = "17"
            composeReports(project.layout.buildDirectory.asFile.get().absolutePath)
            composeForceSkip()
            experimentalContextReceivers()
        }
    }
}

fun KotlinCommonOptions.composeForceSkip() {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true"
    )
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

fun KotlinCommonOptions.expectActualOptIn() {
    freeCompilerArgs += listOf("-Xexpect-actual-classes")
}