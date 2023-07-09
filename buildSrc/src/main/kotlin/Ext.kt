import gradle.kotlin.dsl.accessors._880216c2616ecdf7c3cb978160b24f37.kotlin
import gradle.kotlin.dsl.accessors._880216c2616ecdf7c3cb978160b24f37.sourceSets
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.setupKotlin() {
    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions.jvmTarget = "18"
        kotlinOptions.composeReports(project.buildDir.absolutePath)
    }
}

fun KotlinCommonOptions.composeReports(path: String) {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$path"
    )
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.21"
    )
}

fun Project.projectDependencies(
    androidMainBlock: KotlinDependencyHandler.() -> Unit = {},
    androidUnitTestBlock: (KotlinDependencyHandler.() -> Unit)? = null,
    commonTestBlock: (KotlinDependencyHandler.() -> Unit)? = null,
    commonMainBlock: KotlinDependencyHandler.() -> Unit
) {
    kotlin {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    commonMainBlock()
                }
            }
            if (commonTestBlock != null) {
                val commonTest by getting {
                    dependsOn(commonMain)
                    dependencies {
                        implementation(kotlin("test"))
                        internal {
                            test.junit()
                        }
                        commonTestBlock()
                    }
                }
            }
            val androidMain by getting {
                dependsOn(commonMain)
                dependencies {
                    androidMainBlock()
                }
            }
            if (androidUnitTestBlock != null) {
                val androidUnitTest by getting {
                    dependsOn(androidMain)
                    dependencies {
                        implementation(kotlin("test"))
                        internal {
                            test.junit()
                            test.androidXJunit()
                        }
                        androidMainBlock()
                    }
                }
            }
        }
    }
}

fun KotlinDependencyHandler.external(block: Libs.() -> Unit) {
    val libs = Libs { api(it) }
    libs.block()
}

fun KotlinDependencyHandler.internal(block: Libs.() -> Unit) {
    val libs = Libs { implementation(it) }
    libs.block()
}