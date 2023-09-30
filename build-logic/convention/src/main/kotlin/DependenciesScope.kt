class DependenciesScope {
    internal val commonMain = mutableListOf<SourceDependenciesScope>()
    internal val commonTest = mutableListOf<SourceDependenciesScope>()
    internal val androidMain = mutableListOf<SourceDependenciesScope>()
    internal val androidTest = mutableListOf<SourceDependenciesScope>()
    fun commonMain(block: SourceDependenciesScope.() -> Unit) {
        commonMain.add(generate(block))
    }

    fun commonTest(block: SourceDependenciesScope.() -> Unit) {
        commonTest.add(generate(block))
    }

    fun androidMain(block: SourceDependenciesScope.() -> Unit) {
        androidMain.add(generate(block))
    }

    fun androidTest(block: SourceDependenciesScope.() -> Unit) {
        androidTest.add(generate(block))
    }
    private fun generate(block: SourceDependenciesScope.() -> Unit): SourceDependenciesScope {
        val scope = SourceDependenciesScope()
        scope.block()
        return scope
    }
}