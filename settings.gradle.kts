pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        maven { url = uri("https://jitpack.io") }
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "WallMan"

rootDir
    .walk()
    .filter {
        it.name != "buildSrc" && it.name != "convention"
                && it.isDirectory
                && file("${it.absolutePath}/build.gradle.kts").exists()
    }
    .forEach {
        val rel = it.relativeTo(rootDir).path
        val projectPath = ":" + rel.replace(Regex("""[\\/]+"""), ":")
        include(projectPath)
    }