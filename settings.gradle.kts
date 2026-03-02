pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io/")
        maven("https://maven.crystaelix.com/releases/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.firstdark.dev/releases")
        maven {
            name = "Ornithe Releases"
            url = uri("https://maven.ornithemc.net/releases")
        }
        maven {
            name = "Ornithe Snapshots"
            url = uri("https://maven.ornithemc.net/snapshots")
        }
        maven {
            name = "legacy-fabric"
            url = uri("https://maven.legacyfabric.net/")
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.crystaelix.loom" && requested.version?.startsWith("jitpack-") == true) {
                useModule("com.github.Crystaelix:crystaelix-loom:${requested.version!!.substring(8)}")
            }
        }
    }
}