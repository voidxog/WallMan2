import org.gradle.api.internal.catalog.DelegatingProjectDependency

class ModuleDependenciesScope {
    internal val modules = mutableListOf<DelegatingProjectDependency>()
    operator fun DelegatingProjectDependency.unaryPlus() {
        modules.add(this)
    }
}