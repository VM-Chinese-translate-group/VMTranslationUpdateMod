package top.vmctcn.vmtu.mod.modpack.metadata;

import top.vmctcn.vmtu.mod.modpack.metadata.impl.FTBMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.MPUCMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.SMUCMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.SUCMetadata;

public enum MetadataType {
    FTB("config/metadata.json", "FTBModpackMetadata", new FTBMetadata(), FTBMetadata.class),
    MPUC("config/modpack-update-checker/config.json", "ModpackUpdateCheckerMetadata", new MPUCMetadata(), MPUCMetadata.class),
    SMUC("config/simple-modpack-update-checker.json", "SimpleModpackUpdateCheckerConfiguration", new SMUCMetadata(), SMUCMetadata.class),
    SUC("config/simpleupdatechecker_modpack.json", "SimpleUpdateCheckerConfiguration", new SUCMetadata(), SUCMetadata.class);

    private final String metadataFileName;
    private final String metadataName;
    private final ModpackMetadata metadata;
    private final Class<? extends ModpackMetadata> metadataClass;

    MetadataType(String metadataFileName, String metadataName, ModpackMetadata metadata, Class<? extends ModpackMetadata> metadataClass) {
        this.metadataFileName = metadataFileName;
        this.metadataName = metadataName;
        this.metadata = metadata;
        this.metadataClass = metadataClass;
    }

    public String getMetadataFileName() {
        return metadataFileName;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public ModpackMetadata getMetadata() {
        return metadata;
    }

    public Class<? extends ModpackMetadata> getMetadataClass() {
        return metadataClass;
    }
}
