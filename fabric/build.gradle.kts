plugins {
    id("dev.architectury.loom-no-remap")
    id("com.gradleup.shadow")
    id("com.hypherionmc.modutils.modpublisher")
}

logger.lifecycle("[Fabric|Unobfuscated] Game version: ${stonecutter.current.version}")

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
                    resources {
                        srcDir(common.sourceSets["main"].resources)
                    }
                }
            }
        }
    }
}

repositories {
    maven("https://jitpack.io")
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.nucleoid.xyz/")
    maven("https://maven.architectury.dev")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    implementation("net.fabricmc:fabric-loader:${common.mod.dep("fabric_loader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")

    implementation("me.shedaniel.cloth:cloth-config-fabric:${common.mod.dep("cloth_config")}") {
        exclude("net.fabricmc.fabric-api")
    }

    implementation("com.terraformersmc:modmenu:${common.mod.dep("modmenu")}") {
        exclude("net.fabricmc.fabric-api")
    }

    include("com.github.VM-Chinese-translate-group:VMTUCore:${common.mod.dep("core_version")}")
    implementation("com.github.VM-Chinese-translate-group:VMTUCore:${common.mod.dep("core_version")}")
    implementation("com.google.auto.service:auto-service-annotations:${mod.dep("auto_service")}")
    annotationProcessor("com.google.auto.service:auto-service:${mod.dep("auto_service")}")

    commonBundle(project(common.path)) { isTransitive = false }
    shadowBundle(project(common.path)) { isTransitive = false }
}

java {
    withSourcesJar()

    val requiredJava = when {
        stonecutter.current.parsed >= "26.1" -> JavaVersion.VERSION_25
        stonecutter.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
        stonecutter.current.parsed >= "1.18" -> JavaVersion.VERSION_17
        stonecutter.current.parsed >= "1.17" -> JavaVersion.VERSION_16
        else -> JavaVersion.VERSION_1_8
    }

    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

tasks.jar {
    archiveClassifier = "raw"
}

tasks.shadowJar {
    dependsOn(tasks.jar)
    from(zipTree(tasks.jar.get().archiveFile))
    configurations = listOf(shadowBundle)
    archiveClassifier = null

    isZip64 = true
}

tasks.processResources {
    val clothConfigId = when {
        stonecutter.current.parsed >= "1.18" -> "cloth-config"
        else -> "cloth-config2"
    }

    properties(listOf("fabric.mod.json"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "minecraft" to common.mod.requireProp("mod.mc_dep_fabric"),
        "clothconfig_id" to clothConfigId
    )
}

tasks.register<Copy>("buildAndCollect") {
    from(tasks.shadowJar.get().archiveFile)
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
    artifact = tasks.shadowJar.get()
//    addAdditionalFile(tasks.remapSourcesJar.get())
    modrinthDepends {
        required("fabric-api")
        required("cloth-config")
    }
    curseDepends {
        required("fabric-api")
        required("cloth-config")
    }
}
