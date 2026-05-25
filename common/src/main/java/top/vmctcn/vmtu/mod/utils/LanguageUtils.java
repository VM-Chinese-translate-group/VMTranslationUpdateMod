package top.vmctcn.vmtu.mod.utils;

//? if 1.16.5 {
/*import com.google.common.collect.Sets;
*///?}
import top.vmctcn.vmtu.libraries.modpack.info.api.ModpackInfoHelper;
import top.vmctcn.vmtu.libraries.resourcepack.pack.GameOptionsWriter;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.util.*;

import net.minecraft.client.Minecraft;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

public class LanguageUtils {
    private static final HashMap<String, String[]> FALLBACK_QUEUES = HashMapUtils.newHashMap(8);
    private static final String DEFAULT_LANGUAGE = "en_us";

    public static String getFixedLanguage(String lang) {
        String language = (Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()).toLowerCase();

        if (lang.equals(DEFAULT_LANGUAGE) && !lang.equals(language)) { // Only set language if it's English or another language differing from current
			/*
			Fix language in case it's not a valid language
			code(due to some launchers like Prism Launcher),
			but only works with certain countries.
			Language codes are from Locale.class.
			 */
            Locale[] locales = Arrays.stream(Locale.class.getFields()).filter(f -> f.getType().equals(Locale.class)).map(f -> {
                try {
                    return (Locale) f.get(Locale.getDefault());
                } catch (IllegalAccessException e) {
                    ModContexts.LOGGER.error("Failed to get locale from field {}", f.getName(), e);
                    return null;
                }
            }).filter(Objects::nonNull).filter(l -> !l.getLanguage().isEmpty() && !l.getCountry().isEmpty()).toArray(Locale[]::new); // Get preset locales from java

            String[] languageCodes = Arrays.stream(locales).map(l -> (l.getLanguage() + "_" + l.getCountry()).toLowerCase()).distinct().toArray(String[]::new); // Get preset language codes from locales
            String fixedFrom = language;

            if (Arrays.stream(languageCodes).anyMatch(l -> l.split("_")[1].equals(Locale.getDefault().getCountry().toLowerCase()))) {
                language = Arrays.stream(languageCodes).filter(l -> l.split("_")[1].equals(Locale.getDefault().getCountry().toLowerCase())).findFirst().orElse("en_us");
            }

            /* Language should be fixed */
            ModContexts.LOGGER.info("Switching language to {}{}", language, !fixedFrom.equals(language) ? ", fixed from " + fixedFrom + "." /* Log the unfixed language */ : "...");
            return language;
        }

        return DEFAULT_LANGUAGE;
    }

    public static HashMap<String, String[]> getFallbackQueues() {
        if (FALLBACK_QUEUES.isEmpty()) {
            generateDefaultQueues();
        }

        return FALLBACK_QUEUES;
    }

    private static void generateDefaultQueues() {
        FALLBACK_QUEUES.put("zh_cn", new String[]{"zh_hk", "zh_tw"});
        FALLBACK_QUEUES.put("zh_hk", new String[]{"zh_cn", "zh_tw"});
        FALLBACK_QUEUES.put("zh_tw", new String[]{"zh_cn", "zh_hk"});
        FALLBACK_QUEUES.put("lzh", new String[]{"zh_cn", "zh_hk", "zh_tw"});
    }

    public static boolean isChineseLanguage() {
        //? if >= 1.20.1 {
        String language = Minecraft.getInstance().getLanguageManager().getSelected();
        //?} else if <=1.19.2 {
        /*String language = Minecraft.getInstance().getLanguageManager().getSelected().getCode();
        *///?}
        //? if >1.16.5 {
        Set<String> chineseLangs = Set.of("zh_cn", "zh_tw", "zh_hk", "lzh");
        //?} else {
        /*Set<String> chineseLangs = Sets.newHashSet("zh_cn", "zh_tw", "zh_hk", "lzh");
        *///?}
        return chineseLangs.contains(language);
    }

    public static void autoSwitchLanguage() {
        if (ModConfigHelper.getConfig().misc.autoSwitchLanguage && ModpackInfoHelper.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                String language;

                if (ModpackInfoHelper.isExampleModpackInfo()) {
                    language = (Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()).toLowerCase();
                } else {
                    language = ModpackInfoHelper.getModpackInfo().getModpack().getTranslation().getLanguage();
                }

                GameOptionsWriter writer = new GameOptionsWriter(ModPlatform.getInstance().getGameDir().resolve("options.txt"));
                writer.switchLanguage(LanguageUtils.getFixedLanguage(language));
            } catch (Exception e) {
                ModContexts.LOGGER.warn("Failed to switch language: ", e);
            }
        }
    }
}
