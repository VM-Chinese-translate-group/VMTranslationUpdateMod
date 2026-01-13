plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.gradleup.shadow")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.project.prop("loom.platform")
})

val minecraft: String = stonecutter.current.version

version = "${mod.version}+mc$minecraft"
base.archivesName.set("${mod.id}-common")

loom {
    silentMojangMappingsLicense()

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")

    modImplementation("me.shedaniel.cloth:cloth-config:${mod.dep("cloth_config")}")

    implementation("com.github.VM-Chinese-translate-group:VMTUCore:${mod.dep("core_version")}:all")
    implementation("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")
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

//tasks.register<Copy>("buildAndCollect") {
//    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
//    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/common"))
//    dependsOn(tasks.build)
//}