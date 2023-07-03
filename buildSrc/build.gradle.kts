import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:8.0.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:1.8.21")
    implementation(kotlin("stdlib"))
    gradleApi()
}