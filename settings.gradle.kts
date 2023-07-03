pluginManagement {
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
rootProject.name = "WallMan"
include(":app")
include(":app:benchmark")
//include(":core")
//include(":core-ui")
//include(":settings")
//include(":feature-cache")
