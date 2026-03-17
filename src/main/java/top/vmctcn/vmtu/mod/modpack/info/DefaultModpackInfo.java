package top.vmctcn.vmtu.mod.modpack.info;

public class DefaultModpackInfo extends ModpackInfo {
    DefaultModpack modpack = new DefaultModpack();

    @Override
    public ModpackInfo.Modpack getModpack() {
        return this.modpack;
    }

    public static class DefaultModpack extends ModpackInfo.Modpack {
        String name = "ExampleModpack";
        String version = "0.1.0";
        DefaultTranslation translation = new DefaultTranslation();

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getVersion() {
            return this.version;
        }

        @Override
        public ModpackInfo.Translation getTranslation() {
            return this.translation;
        }
    }

    public static class DefaultTranslation extends ModpackInfo.Translation {
        String id = "example";
        String url = "https://vmct-cn.top/modpacks/example/";
        String language = "zh_cn";
        String version = "1.0.0";

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public String getUrl() {
            return this.url;
        }

        @Override
        public String getLanguage() {
            return this.language;
        }

        @Override
        public String getVersion() {
            return this.version;
        }
    }
}
