package top.vmctcn.vmtu.mod.modpack.metadata.impl;

import top.vmctcn.vmtu.mod.modpack.metadata.MetadataType;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;

public class SMUCMetadata implements ModpackMetadata {
    int configVersion;
    String localVersion;
    String identifier;

    public int getConfigVersion() {
        return configVersion;
    }

    public String getLocalVersion() {
        return localVersion;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getModpackVersion() {
        return localVersion;
    }

    @Override
    public String getModpackName() {
        return "[SMUC NOT SUPPORT]";
    }

    @Override
    public MetadataType getMetadataType() {
        return MetadataType.SMUC;
    }
}
