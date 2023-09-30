import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigurationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("configuration", Configuration::class.java, target)
    }
}