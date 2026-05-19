plugins {
    id("dev.architectury.loom-remap")
    id("com.gradleup.shadow")
    id("com.hypherionmc.modutils.modpublisher")
}

logger.lifecycle("[Forge|Obfuscated] Game version: ${stonecutter.current.version}")

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("common")?.project) {
    "No common project for $project"
}

version = "${mod.version}+mc$minecraft"
base.archivesName.set("${mod.id}-$loader")

val commonBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
}

loom {
    silentMojangMappingsLicense()

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    runConfigs.all {
        isIdeConfigGenerated = true
        runDir = "../../../run"
        vmArgs("-Dmixin.debug.export=true")
    }

    runs {
        getByName("client") {
            // fix runs not set common sources
            sourceSets {
                main {
                    java {
                        srcDir(common.sourceSets["main"].java)
                    }
                    resources {
                        srcDir(common.sourceSets["main"].resources)
                    }
                }
            }
        }
    }
}

repositories {
    maven("https://maven.minecraftforge.net")
    maven("https://jitpack.io")
    maven("https://maven.architectury.dev") {
        content { includeGroup("me.shedaniel.cloth") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.officialMojangMappings())
    "forge"("net.minecraftforge:forge:$minecraft-${common.mod.dep("forge_loader")}")

    modImplementation("me.shedaniel.cloth:cloth-config-forge:${common.mod.dep("cloth_config")}")

    if (stonecutter.current.parsed >= "1.18.2") {
        include("com.github.VM-Chinese-translate-group.VMTULibraries:common:${mod.dep("core_version")}")
        include("com.github.VM-Chinese-translate-group.VMTULibraries:modpack:${mod.dep("core_version")}")
        include("com.github.VM-Chinese-translate-group.VMTULibraries:resourcepack:${mod.dep("core_version")}")
    } else {
        // 1.16.5 MinecraftForge's Jarjar doesn't work with 1.16.5, so we use shadow
        shadowBundle("com.github.VM-Chinese-translate-group.VMTULibraries:common:${mod.dep("core_version")}") { isTransitive = false }
        shadowBundle("com.github.VM-Chinese-translate-group.VMTULibraries:modpack:${mod.dep("core_version")}") { isTransitive = false }
        shadowBundle("com.github.VM-Chinese-translate-group.VMTULibraries:resourcepack:${mod.dep("core_version")}") { isTransitive = false }
    }

    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:common:${mod.dep("core_version")}")
    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:modpack:${mod.dep("core_version")}")
    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:resourcepack:${mod.dep("core_version")}")
    implementation("com.google.auto.service:auto-service-annotations:${mod.dep("auto_service")}")
    annotationProcessor("com.google.auto.service:auto-service:${mod.dep("auto_service")}")

    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path)) { isTransitive = false }
}

java {
    withSourcesJar()

    val requiredJava = when {
        stonecutter.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
        stonecutter.current.parsed >= "1.18" -> JavaVersion.VERSION_17
        stonecutter.current.parsed >= "1.17" -> JavaVersion.VERSION_16
        else -> JavaVersion.VERSION_1_8
    }

    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

tasks.remapJar {
    injectAccessWidener = true
    inputFile = tasks.shadowJar.get().archiveFile
    archiveClassifier = null
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
    exclude("fabric.mod.json", "architectury.common.json")

    isZip64 = true
}

tasks.processResources {
    val clothConfigId = when {
        stonecutter.current.parsed >= "1.17" -> "cloth_config"
        else -> "cloth-config"
    }

    properties(listOf("META-INF/mods.toml", "pack.mcmeta"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to project.version,
        "minecraft" to common.mod.requireProp("mod.mc_dep_forgelike"),
        "clothconfig_id" to clothConfigId
    )
}

tasks.register<Copy>("buildAndCollect") {
    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader"))
    dependsOn(tasks.build)
}

publisher {
    apiKeys {
        modrinth(System.getenv("MODRINTH_TOKEN"))
        curseforge(System.getenv("CURSEFORGE_TOKEN"))
    }

    curseID = common.mod.publish("curseforge")
    modrinthID = common.mod.publish("modrinth")
    versionType = common.mod.publish("version_type")
    changelog = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)
    projectVersion = "$loader-${project.version}"
    displayName = "[${loader.upperCaseFirst()}]${project.version}"
    gameVersions = common.mod.requireProp("mod.mc_targets").split(',')
    loaders = listOf(loader)
    curseEnvironment = common.mod.publish("mod_side")
    artifact = tasks.remapJar.get()
    addAdditionalFile(tasks.remapSourcesJar.get())
    modrinthDepends {
        required("cloth-config")
    }
    curseDepends {
        required("cloth-config")
    }
}
