import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

class ConfigurationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project.pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.multiplatform")
        }
        project.android { setup(project) }
        project.setupKotlin()
    }
}

val PluginDependenciesSpec.config: PluginDependencySpec
    get() = id("configuration")