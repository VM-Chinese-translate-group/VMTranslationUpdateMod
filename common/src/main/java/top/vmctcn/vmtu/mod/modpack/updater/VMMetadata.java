package top.vmctcn.vmtu.mod.modpack.updater;

import java.util.Map;

public class VMMetadata {
    String metaVersion;
    Map<String, Modpacks> modpacks;

    public String getMetaVersion() {
        return metaVersion;
    }

    public Map<String, Modpacks> getModpacks() {
        return modpacks;
    }

    public static class Modpacks {
        String translationVersion;
        String modpackVersion;

        public String getTranslationVersion() {
            return translationVersion;
        }

        public String getModpackVersion() {
            return modpackVersion;
        }
    }
}
