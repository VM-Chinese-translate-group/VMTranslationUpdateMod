pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.firstdark.dev/releases/")
    }
}

val currentStonecutterVersion = file("versions/currentVersion").readText().trim()
val gradleJava = JavaVersion.current()
val requiresJava25 = currentStonecutterVersion >= "26.1"

if (requiresJava25 && !gradleJava.isCompatibleWith(JavaVersion.VERSION_25)) {
    error(
        "Stonecutter currentVersion '$currentStonecutterVersion' requires running Gradle with Java 25, " +
            "but Gradle is currently using Java ${gradleJava.majorVersion}. " +
            "Switch the Gradle JVM to Java 25 or change versions/currentVersion to a Java 21-compatible target such as 1.21.11."
    )
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    //create(rootProject, file("versions/settings.json5"))
    create(rootProject) {
        vcsVersion = "26.1.1"
        branch("common") {
            versions("1.16.5", "1.18.2", "1.19.2", "1.20.1", "1.20.4", "1.20.6", "1.21.1", "1.21.4", "1.21.5", "1.21.8", "1.21.10", "1.21.11").buildscript("obfuscated.gradle.kts")
            versions("26.1.1")
        }
        branch("fabric") {
            versions("1.16.5", "1.18.2", "1.19.2", "1.20.1", "1.20.4", "1.20.6", "1.21.1", "1.21.4", "1.21.5", "1.21.8", "1.21.10", "1.21.11").buildscript("obfuscated.gradle.kts")
            versions("26.1.1")
        }
        branch("neoforge") {
            versions("1.20.4", "1.20.6", "1.21.1", "1.21.4", "1.21.5", "1.21.8", "1.21.10", "1.21.11").buildscript("obfuscated.gradle.kts")
            versions("26.1.1")
        }
        branch("forge") {
            versions("1.16.5", "1.18.2", "1.19.2", "1.20.1")
        }
    }
}

rootProject.name = "VMTranslationUpdateMod"
