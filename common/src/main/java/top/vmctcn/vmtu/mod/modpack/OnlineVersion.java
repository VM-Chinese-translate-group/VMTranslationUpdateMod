package top.vmctcn.vmtu.mod.modpack;

public class OnlineVersion {
    public final String translationVersion;
    public final String modpackVersion;

    public OnlineVersion(String translationVersion, String modpackVersion) {
        this.translationVersion = translationVersion;
        this.modpackVersion = modpackVersion;
    }

    public boolean isValid() {
        return translationVersion != null && !translationVersion.isEmpty();
    }
}
