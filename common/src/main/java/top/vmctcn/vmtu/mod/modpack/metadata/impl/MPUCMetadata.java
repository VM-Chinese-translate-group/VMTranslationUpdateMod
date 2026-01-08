package top.vmctcn.vmtu.mod.modpack.metadata.impl;

import top.vmctcn.vmtu.mod.modpack.metadata.MetadataType;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;

import java.util.List;

public class MPUCMetadata implements ModpackMetadata {
    int schemaVersion;
    String currentVersion;
    String modpackName;
    String modpackAuthor;
    String modpackReleaseType;
    String githubRepo;
    Advanced advanced;

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getModpackAuthor() {
        return modpackAuthor;
    }

    public String getModpackReleaseType() {
        return modpackReleaseType;
    }

    public String getGithubRepo() {
        return githubRepo;
    }

    public Advanced getAdvanced() {
        return advanced;
    }

    @Override
    public String getModpackVersion() {
        return currentVersion;
    }

    @Override
    public String getModpackName() {
        return modpackName;
    }

    @Override
    public MetadataType getMetadataType() {
        return MetadataType.MPUC;
    }

    public static class Advanced {
        boolean bccEnabled;
        boolean expandButton;
        int updateCheckerType;
        boolean addButton;
        boolean mainMenuCreditsIntegration;
        List<String> modpackMods;

        public boolean isBccEnabled() {
            return bccEnabled;
        }

        public boolean isExpandButton() {
            return expandButton;
        }

        public int getUpdateCheckerType() {
            return updateCheckerType;
        }

        public boolean isAddButton() {
            return addButton;
        }

        public boolean isMainMenuCreditsIntegration() {
            return mainMenuCreditsIntegration;
        }

        public List<String> getModpackMods() {
            return modpackMods;
        }
    }
}
