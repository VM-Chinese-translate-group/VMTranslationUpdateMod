package top.vmctcn.vmtu.mod.modpack;

public class OnlineVersion {
    private final String translationVersion;
    private final String modpackVersion;

    public OnlineVersion(String translationVersion, String modpackVersion) {
        this.translationVersion = translationVersion;
        this.modpackVersion = modpackVersion;
    }

    public String translationVersion() {
        return translationVersion;
    }

    public String modpackVersion() {
        return modpackVersion;
    }

    public boolean isValid() {
        return translationVersion != null && !translationVersion.isEmpty();
    }
}
