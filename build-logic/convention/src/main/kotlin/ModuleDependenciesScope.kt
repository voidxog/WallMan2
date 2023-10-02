import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

class ModuleDependenciesScope {
    internal val modules = mutableListOf<DelegatingProjectDependency>()
    operator fun DelegatingProjectDependency.unaryPlus() {
        modules.add(this)
    }
}

fun DependencyHandlerScope.moduleDependencies(block: ModuleDependenciesScope.() -> Unit) {
    val scope = ModuleDependenciesScope()
    scope.block()
    scope.modules.forEach {
        "implementation"(it)
    }
}