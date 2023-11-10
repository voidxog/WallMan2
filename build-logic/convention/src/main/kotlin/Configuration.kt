import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findPlugin
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

open class Configuration {
    internal val dependencies = mutableListOf<DependenciesScope>()

    var namespace = ""


    fun Configuration.dependencies(block: DependenciesScope.() -> Unit) {
        val scope = DependenciesScope()
        scope.block()
        dependencies.add(scope)
    }
}

internal fun Configuration.apply(project: Project) {
    val modules = dependencies.flatMap { it.modules }
    val commonMainDeps = dependencies.flatMap { it.commonMain }
    val commonTestDeps = dependencies.flatMap { it.commonTest }
    val androidMainDeps = dependencies.flatMap { it.androidMain }
    val androidTestDeps = dependencies.flatMap { it.androidTest }

    project.kotlin {
        androidTarget()
        sourceSets.all {
            languageSettings {
                languageVersion = "2.0"
            }
        }
        sourceSets.apply {
            val commonMain by getting {
                dependencies {
                    modules.forEach {
                        implementation(it)
                    }
                }
                configure(commonMainDeps)
            }
            if (commonTestDeps.isNotEmpty()) {
                val commonTest by getting {
                    dependencies {
                        implementation(kotlin("test"))
                    }
                    configure(commonTestDeps)
                }
            }
            val androidMain by getting {
                configure(androidMainDeps)
            }
            if (androidTestDeps.isNotEmpty()) {
                val androidTest by getting {
                    dependencies {
                        implementation(kotlin("test"))
                    }
                    configure(androidTestDeps)
                }
            }
        }
    }

    project.android {
        this.namespace = "com.colorata.wallman.${this@apply.namespace}"
    }
}

// Using this block because not able to callback
// right after configure(it breaks android namespace)
fun Project.configuration(block: Configuration.() -> Unit) {
    val configuration = Configuration()
    configuration.block()
    configuration.apply(this)
}

inline fun <reified T : Plugin<*>> Project.plugin(): T? = plugins.findPlugin(T::class)

fun Configuration.internal(block: VisibilityDependenciesScope.() -> Unit) {
    commonMain {
        internal(block)
    }
}

fun Configuration.external(block: VisibilityDependenciesScope.() -> Unit) {
    commonMain {
        external(block)
    }
}

fun Configuration.modules(block: ModuleDependenciesScope.() -> Unit) {
    dependencies {
        modules(block)
    }
}

internal val Project.libs: VersionCatalog
    get() =
        extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")

internal fun Project.getVersion(name: String): String {
    return libs
        .findVersion(name)
        .get()
        .displayName
}

fun Project.gradleProperty(name: String): String? = providers.gradleProperty(name).orNull

internal fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(KotlinMultiplatformExtension::class.java, block)
}

internal fun Project.android(block: LibraryExtension.() -> Unit) {
    extensions.configure(LibraryExtension::class.java, block)
}

fun Configuration.commonMain(block: SourceDependenciesScope.() -> Unit) {
    dependencies {
        commonMain(block)
    }
}

fun Configuration.commonTest(block: SourceDependenciesScope.() -> Unit) {
    dependencies {
        commonTest(block)
    }
}

fun Configuration.androidMain(block: SourceDependenciesScope.() -> Unit) {
    dependencies {
        androidMain(block)
    }
}

fun Configuration.androidTest(block: SourceDependenciesScope.() -> Unit) {
    dependencies {
        androidTest(block)
    }
}

internal fun KotlinSourceSet.configure(dependencies: List<SourceDependenciesScope>) {
    dependencies {
        dependencies.forEach {
            it.internalDependencies.forEach { dep ->
                implementation(dep)
            }
            it.externalDependencies.forEach { dep ->
                api(dep)
            }
        }
    }
}