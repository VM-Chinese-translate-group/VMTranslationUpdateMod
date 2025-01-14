package top.vmctcn.vmtucore;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtucore.respack.GameOptionsWriter;
import top.vmctcn.vmtucore.respack.MetadataReader;
import top.vmctcn.vmtucore.respack.ResPackConverter;
import top.vmctcn.vmtucore.respack.ResPackDownloader;
import top.vmctcn.vmtucore.respack.metadata.GameAssetDetail;
import top.vmctcn.vmtucore.util.FileUtil;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Under AGPL License
 *
 * @author CFPAOrg/I18nUpdateMod3 (xfl03)
 */
public class VMTUCore {
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTUCore");
    public static String MOD_VERSION;
    public static final Gson GSON = new Gson();
    public static final String LOCAL_PATH = "vmtu";

    public static void init(Path gamePath, String gameVersion, String packName, String packSource) {
        try (InputStream is = VMTUCore.class.getResourceAsStream("/metadata.json")) {
            MOD_VERSION = GSON.fromJson(new InputStreamReader(is), JsonObject.class).get("version").getAsString();
        } catch (Exception e) {
            LOGGER.warn("Error getting version: " + e);
        }

        String localStorage = getLocalStoragePos();

        FileUtil.setResourcePackDirPath(gamePath.resolve("resourcepacks"));

        int gameMajorVersion = Integer.parseInt(gameVersion.split("\\.")[1]);

        try {
            //Get asset
            GameAssetDetail assets = MetadataReader.getAssetDetail(gameVersion, packSource);

            //Update resource pack
            List<ResPackDownloader> languagePacks = new ArrayList<>();
            boolean convertNotNeed = assets.downloads.size() == 1 && assets.downloads.get(0).targetVersion.equals(gameVersion);
            String applyFileName = assets.downloads.get(0).fileName;
            for (GameAssetDetail.AssetDownloadDetail it : assets.downloads) {
                FileUtil.setTemporaryDirPath(Paths.get(localStorage, LOCAL_PATH, it.targetVersion));
                ResPackDownloader languagePack = new ResPackDownloader(it.fileName, convertNotNeed);
                languagePack.checkUpdate(it.fileUrl);
                languagePacks.add(languagePack);
            }

            //Convert resourcepack
            if (!convertNotNeed) {
                FileUtil.setTemporaryDirPath(Paths.get(localStorage, LOCAL_PATH, gameVersion));
                applyFileName = assets.covertFileName;
                ResPackConverter converter = new ResPackConverter(languagePacks, applyFileName);
                converter.convert(assets.covertPackFormat, getResourcePackDescription(assets.downloads));
            }

            //Apply resource pack
            GameOptionsWriter config = new GameOptionsWriter(gamePath.resolve("options.txt"));
            config.addResourcePack(packName,
                    (gameMajorVersion <= 12 ? "" : "file/") + applyFileName);
            config.writeToFile();
        } catch (Exception e) {
            LOGGER.warn("Failed to update resource pack: ", e);
        }
    }

    private static String getResourcePackDescription(List<GameAssetDetail.AssetDownloadDetail> downloads) {
        return downloads.size() > 1 ?
                String.format("该包由%s版本合并\n作者：VM汉化组",
                        downloads.stream().map(it -> it.targetVersion).collect(Collectors.joining("和"))) :
                String.format("该包对应的官方支持版本为%s\n作者：VM汉化组",
                        downloads.get(0).targetVersion);

    }

    public static String getLocalStoragePos() {
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path oldPath = userHome.resolve(LOCAL_PATH);
        if (Files.exists(oldPath)) {
            return userHome.toString();
        }

        // https://developer.apple.com/documentation/foundation/url/3988452-applicationsupportdirectory#discussion
        String macAppSupport = System.getProperty("os.name").contains("OS X") ?
                userHome.resolve("Library/Application Support").toString() : null;
        String localAppData = System.getenv("LocalAppData");

        // XDG_DATA_HOME fallbacks to ~/.local/share
        // https://specifications.freedesktop.org/basedir-spec/latest/#variables
        String xdgDataHome = System.getenv("XDG_DATA_HOME");
        if (xdgDataHome == null) {
            xdgDataHome = userHome.resolve(".local/share").toString();
        }

        return Stream.of(localAppData, macAppSupport).filter(Objects::nonNull).findFirst().orElse(xdgDataHome);
    }
}
