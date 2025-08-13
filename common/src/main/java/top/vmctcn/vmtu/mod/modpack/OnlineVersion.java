package top.vmctcn.vmtu.mod.modpack;

public record OnlineVersion(String translationVersion, String modpackVersion) {
    public boolean isValid() {
        return translationVersion != null && !translationVersion.isEmpty();
    }
}
