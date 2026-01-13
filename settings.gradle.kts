pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots") {
            name = "KikuGie Snapshots"
        }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.8.2"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    create(rootProject, file("versions/settings.json5"))
}

rootProject.name = "StonecutterTemplateArchitectury"
