package com.colorata.wallman.buildSrc

import gradle.kotlin.dsl.accessors._880216c2616ecdf7c3cb978160b24f37.implementation
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinDependencyHandler.modules(block: Modules.() -> Unit) {
    val modules = Modules {
        implementation(project(it))
    }
    modules.block()
}

fun DependencyHandlerScope.projectModules(block: Modules.() -> Unit) {
    val modules = Modules {
        implementation(project(it))
    }
    modules.block()
}

class Modules(private val implementation: (moduleName: String) -> Unit) {


    fun shared() = implementation(":shared")

    val core = Core()
    inner class Core {
        private val prefix = ":common:core"
        fun di() = implementation("$prefix:di")
        fun data() = implementation("$prefix:data")
        fun ui() = implementation("$prefix:ui")
        fun impl() = implementation("$prefix:impl")
    }

    val wallpapers = Wallpapers()
    inner class Wallpapers {
        private val prefix = ":common:wallpapers"
        fun api() = implementation("$prefix:api")
        fun impl() = implementation("$prefix:impl")
        fun ui() = implementation("$prefix:ui")
    }

    val categories = Categories()
    inner class Categories {
        private val prefix = ":common:categories"
        fun api() = implementation("$prefix:api")
        fun ui() = implementation("$prefix:ui")
    }

    val settings = Settings()
    inner class Settings {
        val overview = Overview()
        inner class Overview {
            private val prefix = ":common:settings:overview"
            fun api() = implementation("$prefix:api")
            fun ui() = implementation("$prefix:ui")
        }

        val memory = Memory()
        inner class Memory {
            private val prefix = ":common:settings:memory"
            fun api() = implementation("$prefix:api")
            fun ui() = implementation("$prefix:ui")
        }

        val mirror = Mirror()
        inner class Mirror {
            private val prefix = ":common:settings:mirror"
            fun api() = implementation("$prefix:api")
            fun ui() = implementation("$prefix:ui")
        }

        val about = About()
        inner class About {
            private val prefix = ":common:settings:about"
            fun api() = implementation("$prefix:api")
            fun ui() = implementation("$prefix:ui")
        }
    }
}