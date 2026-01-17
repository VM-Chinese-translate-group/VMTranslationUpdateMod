package top.vmctcn.vmtu.mod.modpack.metadata;

import top.vmctcn.vmtu.mod.modpack.metadata.impl.FTBMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.MPUCMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.SMUCMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.impl.SUCMetadata;

public enum MetadataType {
    FTB("config/metadata.json", new FTBMetadata(), FTBMetadata.class),
    MPUC("config/modpack-update-checker/config.json", new MPUCMetadata(), MPUCMetadata.class),
    SMUC("config/simple-modpack-update-checker.json", new SMUCMetadata(), SMUCMetadata.class),
    SUC("config/simpleupdatechecker_modpack.json", new SUCMetadata(), SUCMetadata.class);

    private final String metadataFileName;
    private final ModpackMetadata metadata;
    private final Class<? extends ModpackMetadata> metadataClass;

    MetadataType(String metadataFileName, ModpackMetadata metadata, Class<? extends ModpackMetadata> metadataClass) {
        this.metadataFileName = metadataFileName;
        this.metadata = metadata;
        this.metadataClass = metadataClass;
    }

    public String getMetadataFileName() {
        return metadataFileName;
    }

    public ModpackMetadata getMetadata() {
        return metadata;
    }

    public Class<? extends ModpackMetadata> getMetadataClass() {
        return metadataClass;
    }
}
