import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import javax.inject.Inject

abstract class Configuration @Inject constructor(private val project: Project) {
    fun dependencies(block: DependenciesScope.() -> Unit) {
        val scope = DependenciesScope()
        scope.block()

        project.kotlin {
            androidTarget()
            sourceSets.apply {
                val commonMain by getting {
                    dependencies {
                        scope.modules.forEach {
                            implementation(it)
                        }
                    }
                    configure(scope.commonMain)
                }
                if (scope.commonTest.isNotEmpty()) {
                    val commonTest by getting {
                        dependsOn(commonMain)
                        dependencies {
                            implementation(kotlin("test"))
                        }
                        configure(scope.commonTest)
                    }
                }
                val androidMain by getting {
                    dependsOn(commonMain)
                    configure(scope.androidMain)
                }
                if (scope.androidTest.isNotEmpty()) {
                    val androidTest by getting {
                        dependsOn(androidMain)
                        dependencies {
                            implementation(kotlin("test"))
                        }
                        configure(scope.commonTest)
                    }
                }
            }
        }
    }

    var namespace = ""
        set(value) {
            field = value
            project.android {
                this.namespace = "com.colorata.wallman.$value"
            }
        }
}

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

private fun KotlinSourceSet.configure(dependencies: List<SourceDependenciesScope>) {
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