package top.vmctcn.vmtu.mod.modpack.info;

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
        String id;
        String url;
        String language;
        String version;
        @Deprecated String updateCheckUrl;
        String resourcePackName;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getLanguage() {
            return language;
        }

        public String getVersion() {
            return version;
        }

        @Deprecated
        public String getUpdateCheckUrl() {
            return updateCheckUrl;
        }

        public String getResourcePackName() {
            return resourcePackName;
        }
    }
}
