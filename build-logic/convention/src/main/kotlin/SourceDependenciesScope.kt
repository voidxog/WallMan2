import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

class SourceDependenciesScope {
    internal val externalDependencies = mutableListOf<Provider<MinimalExternalModuleDependency>>()
    internal val internalDependencies = mutableListOf<Provider<MinimalExternalModuleDependency>>()
    fun external(block: VisibilityDependenciesScope.() -> Unit) {
        externalDependencies.addAll(generateDependencies(block))
    }

    fun internal(block: VisibilityDependenciesScope.() -> Unit) {
        internalDependencies.addAll(generateDependencies(block))
    }

    private fun generateDependencies(block: VisibilityDependenciesScope.() -> Unit): List<Provider<MinimalExternalModuleDependency>> {
        val scope = VisibilityDependenciesScope()
        scope.block()
        return scope.dependencies
    }
}