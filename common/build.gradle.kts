plugins {
    id("dev.architectury.loom-no-remap")
}

logger.lifecycle("[Common|Unobfuscated] Game version: ${stonecutter.current.version}")

val minecraft: String = stonecutter.current.version

version = "${mod.version}+mc$minecraft"
base.archivesName.set("${mod.id}-common")

loom {
//    decompilers {
//        get("vineflower").apply { // Adds names to lambdas - useful for mixins
//            options.put("mark-corresponding-synthetics", "1")
//        }
//    }
}

repositories {
    maven("https://jitpack.io")
    maven("https://maven.architectury.dev")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    implementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")

    implementation("me.shedaniel.cloth:cloth-config:${mod.dep("cloth_config")}")

    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:common:${mod.dep("core_version")}")
    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:modpack:${mod.dep("core_version")}")
    implementation("com.github.VM-Chinese-translate-group.VMTULibraries:resourcepack:${mod.dep("core_version")}")
    implementation("com.google.auto.service:auto-service-annotations:${mod.dep("auto_service")}")
    annotationProcessor("com.google.auto.service:auto-service:${mod.dep("auto_service")}")
}

java {
    withSourcesJar()

    val requiredJava = when {
        stonecutter.current.parsed >= "26.1.2" -> JavaVersion.VERSION_25
        stonecutter.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
        stonecutter.current.parsed >= "1.18" -> JavaVersion.VERSION_17
        stonecutter.current.parsed >= "1.17" -> JavaVersion.VERSION_16
        else -> JavaVersion.VERSION_1_8
    }

    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

//tasks.register<Copy>("buildAndCollect") {
//    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
//    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/common"))
//    dependsOn(tasks.build)
//}