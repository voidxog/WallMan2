import org.gradle.api.Project

class ProjectConfiguration(project: Project) {
    val id = project.gradleProperty("app.id") ?: propertiesError("app.id")
    val targetSdk =
        project.gradleProperty("app.targetSdk")?.toIntOrNull()
            ?: propertiesError("app.targetSdk")
    val compileSdk =
        project.gradleProperty("app.compileSdk")?.toIntOrNull()
            ?: propertiesError("app.compileSdk")
    val minSdk =
        project.gradleProperty("app.minSdk")?.toIntOrNull()
            ?: propertiesError("app.minSdk")

    val versionCode =
        project.gradleProperty("app.versionCode")?.toIntOrNull()
            ?: propertiesError("app.versionCode")
    val versionName =
        project.gradleProperty("app.versionName")
            ?: propertiesError("app.versionName")

    val storePassword =
        project.gradleProperty("signing.storePassword")
            ?: propertiesError("signing.storePassword")
    val keyAlias =
        project.gradleProperty("signing.keyAlias")
            ?: propertiesError("signing.keyAlias")
    val keyPassword =
        project.gradleProperty("signing.keyPassword")
            ?: propertiesError("signing.keyPassword")
}

val Project.projectConfiguration: ProjectConfiguration
    get() = ProjectConfiguration(this)

private fun propertiesError(name: String): Nothing = error("$name is not set in gradle.properties")