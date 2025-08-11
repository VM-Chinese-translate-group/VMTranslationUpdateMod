package top.vmctcn.vmtu.mod.modpack.meta;

import java.util.Map;

public class Metadata {
    String metaVersion;
    Map<String, Modpacks> modpacks;

    public String getMetaVersion() {
        return metaVersion;
    }

    public Map<String, Modpacks> getModpacks() {
        return modpacks;
    }

    public static class Modpacks {
        String name;
        String translationVersion;
        String modpackVersion;

        public String getName() {
            return name;
        }

        public String getTranslationVersion() {
            return translationVersion;
        }

        public String getModpackVersion() {
            return modpackVersion;
        }
    }
}
