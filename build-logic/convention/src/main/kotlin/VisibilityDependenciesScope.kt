import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

class VisibilityDependenciesScope {
    val dependencies = mutableListOf<Provider<MinimalExternalModuleDependency>>()

    operator fun Provider<MinimalExternalModuleDependency>.invoke() {
        dependencies.add(this)
    }

    operator fun Provider<MinimalExternalModuleDependency>.unaryPlus() {
        invoke()
    }
}