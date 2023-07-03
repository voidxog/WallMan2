package com.colorata.wallman.buildSrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.hilt() {
    add("implementation", Libraries.Hilt.android)
    add("kapt", Libraries.Hilt.androidCompiler)
    add("kapt", Libraries.Hilt.compiler)
    add("implementation", Libraries.Hilt.navigationCompose)
}