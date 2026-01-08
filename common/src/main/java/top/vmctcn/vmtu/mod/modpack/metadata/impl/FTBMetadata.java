package top.vmctcn.vmtu.mod.modpack.metadata.impl;

import top.vmctcn.vmtu.mod.modpack.metadata.MetadataType;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;

public class FTBMetadata implements ModpackMetadata {
    int id; // Modpack ID
    String name; // Modpack Name
    String uid; // Modpack UID
    String slug;
    String type; // Modpack Type
    Version version;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getSlug() {
        return slug;
    }

    public String getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String getModpackVersion() {
        return version.name;
    }

    @Override
    public String getModpackName() {
        return name;
    }

    @Override
    public MetadataType getMetadataType() {
        return MetadataType.FTB;
    }

    public static class Version {
        int id;  // Version ID
        String uid; // Version UID
        String name; // Version Name
        String type; // Version Type

        public int getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}
