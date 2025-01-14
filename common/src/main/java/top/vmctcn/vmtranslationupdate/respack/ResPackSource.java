package top.vmctcn.vmtranslationupdate.respack;

public enum ResPackSource {
    GITEE("https://gitee.com/Wulian233/vmtu/raw/main/resourcepack/");

    private final String sourceUrl;

    ResPackSource(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getUrl() {
        return this.sourceUrl;
    }
}
