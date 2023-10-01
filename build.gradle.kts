
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("configuration") apply false
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
        classpath("androidx.benchmark:benchmark-baseline-profile-gradle-plugin:1.2.0-beta01")
    }
}