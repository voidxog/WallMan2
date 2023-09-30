import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ConfigurationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.multiplatform")
        }
        target.extensions.create("configuration", Configuration::class.java, target)
        target.android { setup(target) }
        target.setupKotlin()
    }
}