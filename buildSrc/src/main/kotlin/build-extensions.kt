import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import org.gradle.language.jvm.tasks.ProcessResources

val Project.mod: ModData get() = ModData(this)
fun Project.prop(key: String): String? = findProperty(key)?.toString()
fun String.upperCaseFirst() = replaceFirstChar { if (it.isLowerCase()) it.uppercaseChar() else it }

fun RepositoryHandler.strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
    forRepository { maven(url) { name = alias } }
    filter { groups.forEach(::includeGroup) }
}

fun ProcessResources.properties(files: Iterable<String>, vararg properties: Pair<String, Any>) {
    for ((name, value) in properties) inputs.property(name, value)
    filesMatching(files) {
        expand(properties.toMap())
    }
}

@JvmInline
value class ModData(private val project: Project) {
    val id: String get() = requireProp("mod.id")
    val name: String get() = requireProp("mod.name")
    val version: String get() = requireProp("mod.version")
    val group: String get() = requireProp("mod.group")

    fun requireProp(key: String) = requireNotNull(project.prop(key)) { "Missing '$key'" }
    fun dep(key: String) = requireProp("deps.$key")
}