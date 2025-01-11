package top.vmctcn.vmtucore.respack;

import com.google.gson.Gson;
import top.vmctcn.vmtucore.VMTUCore;
import top.vmctcn.vmtucore.respack.metadata.AssetMetadata;
import top.vmctcn.vmtucore.respack.metadata.GameAssetDetail;
import top.vmctcn.vmtucore.respack.metadata.GameMetadata;
import top.vmctcn.vmtucore.respack.metadata.Metadata;
import top.vmctcn.vmtucore.util.version.Version;
import top.vmctcn.vmtucore.util.version.VersionRange;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Under AGPL License
 *
 * @author CFPAOrg/I18nUpdateMod3 (xfl03)
 */
public class MetadataReader {
    private static final Gson GSON = new Gson();
    private static Metadata metadata;

    static {
        init();
    }

    private static void init() {
        try (InputStream is = MetadataReader.class.getResourceAsStream("/metadata.json")) {
            if (is != null) {
                metadata = GSON.fromJson(new InputStreamReader(is), Metadata.class);
            } else {
                VMTUCore.LOGGER.warn("Error getting index: is is null");
            }
        } catch (Exception e) {
            VMTUCore.LOGGER.warn("Error getting index: " + e);
        }
    }

    private static GameMetadata getGameMetaData(String mcVersion) {
        Version version = Version.from(mcVersion);
        return metadata.games.stream().filter(it -> {
            VersionRange range = new VersionRange(it.gameVersions);
            return range.contains(version);
        }).findFirst().orElseThrow(() -> new IllegalStateException(String.format("Version %s not found in i18n meta", mcVersion)));
    }

    private static AssetMetadata getAssetMetaData(String gameVersion) {
        List<AssetMetadata> current = metadata.assets.stream()
                .filter(it -> it.targetVersion.equals(gameVersion))
                .toList();
        return current.stream().findFirst().orElseGet(() -> current.get(0));
    }

    public static GameAssetDetail getAssetDetail(String gameVersion, String packSource) {
        GameMetadata convert = getGameMetaData(gameVersion);
        GameAssetDetail ret = new GameAssetDetail();

        ret.downloads = convert.convertFrom.stream().map(MetadataReader::getAssetMetaData).map(it -> {
            GameAssetDetail.AssetDownloadDetail adi = new GameAssetDetail.AssetDownloadDetail();
            adi.fileName = it.filename;
            adi.fileUrl = packSource + it.filename;
            adi.targetVersion = it.targetVersion;
            return adi;
        }).collect(Collectors.toList());
        ret.covertPackFormat = convert.packFormat;
        ret.covertFileName = String.format("VMTranslationPack-Converted-%s.zip", gameVersion);
        return ret;
    }
}
