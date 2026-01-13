package top.vmctcn.vmtu.mod.modpack.updater;

//? if >=1.18.2 {
public record OnlineVersion(String translationVersion, String modpackVersion) {
    public boolean isValid() {
        return translationVersion != null && !translationVersion.isEmpty();
    }
}
//?} else {
/*public class OnlineVersion {
    private final String translationVersion;
    private final String modpackVersion;

    public OnlineVersion(String translationVersion, String modpackVersion) {
        this.translationVersion = translationVersion;
        this.modpackVersion = modpackVersion;
    }

    public boolean isValid() {
        return translationVersion != null && !translationVersion.isEmpty();
    }

    public String translationVersion() {
        return translationVersion;
    }

    public String modpackVersion() {
        return modpackVersion;
    }
}
*///?}
