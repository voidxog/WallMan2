import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory
import org.gradle.api.provider.Provider

class VisibilityDependenciesScope {
    val dependencies = mutableListOf<Provider<MinimalExternalModuleDependency>>()

    operator fun Provider<MinimalExternalModuleDependency>.unaryPlus() {
        dependencies.add(this)
    }

    operator fun ExternalModuleDependencyFactory.DependencyNotationSupplier.unaryPlus() {
        dependencies.add(asProvider())
    }
}