package top.vmctcn.vmtu.mod.modpack.info;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModpackInfo {
    Modpack modpack;

    public Modpack getModpack() {
        return modpack;
    }

    public static class Modpack {
        String name;
        String version;
        Translation translation;

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public Translation getTranslation() {
            return translation;
        }
    }

    public static class Translation {
        String url;
        String language;
        ArrayList<String> supportLanguages;
        String version;
        @Nullable String updateCheckUrl;
        String resourcePackName;

        public String getUrl() {
            return url;
        }

        public String getLanguage() {
            return language;
        }

        public ArrayList<String> getSupportLanguages() {
            return supportLanguages;
        }

        public String getVersion() {
            return version;
        }

        public @Nullable String getUpdateCheckUrl() {
            return updateCheckUrl;
        }

        public String getResourcePackName() {
            return resourcePackName;
        }
    }
}