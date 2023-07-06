package com.colorata.wallman.buildSrc

import App
import com.android.build.api.dsl.LibraryExtension
import gradle.kotlin.dsl.accessors._880216c2616ecdf7c3cb978160b24f37.kotlin
import gradle.kotlin.dsl.accessors._880216c2616ecdf7c3cb978160b24f37.sourceSets
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.getValue
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun LibraryExtension.setup() {
    compileSdk = App.compileSdk
    defaultConfig {
        minSdk = App.minSdk
    }
}

fun Project.kotlinDependencies(
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
                        implementation(Libraries.Test.junit)
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
                        implementation(Libraries.Test.junit)
                        implementation(Libraries.Test.androidXJunit)
                        androidMainBlock()
                    }
                }
            }
        }
    }
}