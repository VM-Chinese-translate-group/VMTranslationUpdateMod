package top.vmctcn.vmtu.mod.modpack.info;

public class DefaultModpackInfo extends ModpackInfo {
    public DefaultModpackInfo() {
        this.modpack = new DefaultModpack();
    }

    @Override
    public ModpackInfo.Modpack getModpack() {
        return this.modpack;
    }

    public static class DefaultModpack extends ModpackInfo.Modpack {
        public DefaultModpack() {
            this.name = "ExampleModpack";
            this.version = "0.1.0";
            this.translation = new DefaultTranslation();
        }

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
        public DefaultTranslation() {
            this.id = "example";
            this.url = "https://vmct-cn.top/modpacks/example/";
            this.language = "zh_cn";
            this.version = "1.0.0";
        }

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
