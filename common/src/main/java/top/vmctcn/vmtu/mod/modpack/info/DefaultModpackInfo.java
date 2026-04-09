package top.vmctcn.vmtu.mod.modpack.info;

public class DefaultModpackInfo extends ModpackInfo {
    public DefaultModpackInfo() {
        this.modpack = new DefaultModpack();
    }

    @Override
    public ModpackInfo.Modpack getModpack() {
        return super.getModpack();
    }

    public static class DefaultModpack extends ModpackInfo.Modpack {
        public DefaultModpack() {
            this.name = "ExampleModpack";
            this.version = "0.1.0";
            this.translation = new DefaultTranslation();
        }

        @Override
        public String getName() {
            return super.getName();
        }

        @Override
        public String getVersion() {
            return super.getVersion();
        }

        @Override
        public ModpackInfo.Translation getTranslation() {
            return super.getTranslation();
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
            return super.getId();
        }

        @Override
        public String getUrl() {
            return super.getUrl();
        }

        @Override
        public String getLanguage() {
            return super.getLanguage();
        }

        @Override
        public String getVersion() {
            return super.getVersion();
        }
    }
}
