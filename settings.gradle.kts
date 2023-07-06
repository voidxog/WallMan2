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
include(":shared")
include(":common:core:data")
include(":common:core:impl")
include(":common:core:di")
include(":common:core:ui")
include(":common:wallpapers:api")
include(":common:wallpapers:impl")
include(":common:wallpapers:ui")
include(":common:categories:api")
include(":common:categories:ui")
include(":common:settings:overview:api")
include(":common:settings:overview:ui")
include(":common:settings:memory:api")
include(":common:settings:memory:ui")
include(":common:settings:mirror:api")
include(":common:settings:mirror:ui")
include(":common:settings:about:api")
include(":common:settings:about:ui")