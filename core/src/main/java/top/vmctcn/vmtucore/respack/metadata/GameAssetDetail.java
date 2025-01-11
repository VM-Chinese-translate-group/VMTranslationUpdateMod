package top.vmctcn.vmtucore.respack.metadata;

import java.util.List;

/**
 * Under AGPL License
 *
 * @author CFPAOrg/I18nUpdateMod3 (xfl03)
 */
public class GameAssetDetail {
    public List<AssetDownloadDetail> downloads;
    public Integer covertPackFormat;
    public String covertFileName;

    public static class AssetDownloadDetail {
        public String fileName;
        public String fileUrl;
        public String targetVersion;
    }
}
