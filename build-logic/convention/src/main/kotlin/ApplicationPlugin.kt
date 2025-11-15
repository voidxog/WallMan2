import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project.pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("androidx.baselineprofile")
        }
        project.configureApp {
            val config = project.projectConfiguration
            namespace = config.id
            compileSdk = config.compileSdk
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            signingConfigs {
                create("release") {
                    storeFile = project.file("${project.rootProject.projectDir}/key.jks")
                    storePassword = config.storePassword
                    keyAlias = config.keyAlias
                    keyPassword = config.keyPassword
                }
            }
            defaultConfig {
                applicationId = config.id
                minSdk = config.minSdk
                compileSdk = config.compileSdk
                targetSdk = config.targetSdk
                versionCode = config.versionCode
                versionName = config.versionName

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
                signingConfig = signingConfigs.getByName("release")
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    signingConfig = signingConfigs.getByName("release")
                    proguardFiles(
                        getDefaultProguardFile("proguard-android.txt"),
                        "proguard-rules.pro"
                    )
                }
                getByName("nonMinifiedRelease") {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            buildFeatures {
                compose = true
                buildConfig = false
            }
            composeOptions {
                kotlinCompilerExtensionVersion =
                    project.libs.findVersion("compose_compiler").get().displayName
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}

private fun Project.configureApp(block: ApplicationExtension.() -> Unit) {
    project.configure<ApplicationAndroidComponentsExtension> {
        finalizeDsl {
            it.block()
        }
    }
}