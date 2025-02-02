package top.vmctcn.vmtranslationupdate.modpack;

public class ModpackInfo {
    private Modpack modpack;

    public Modpack getModpack() {
        return modpack;
    }

    public static class Modpack {
        private String name;
        private String version;
        private Translation translation;

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
        private String url;
        private String language;
        private String version;
        private String updateCheckUrl;
        private String resourcePackName;

        public String getUrl() {
            return url;
        }

        public String getLanguage() {
            return language;
        }

        public String getVersion() {
            return version;
        }

        public String getUpdateCheckUrl() {
            return updateCheckUrl;
        }

        public String getResourcePackName() {
            return resourcePackName;
        }
    }
}