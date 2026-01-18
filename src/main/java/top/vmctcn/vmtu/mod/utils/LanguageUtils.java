package top.vmctcn.vmtu.mod.utils;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import top.vmctcn.vmtu.core.pack.GameOptionsWriter;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class LanguageUtils {
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
                    ModContexts.LOGGER.error("Failed to get locale from field" + f.getName(), e);
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

    public static boolean isChineseLanguage() {
        String language = Minecraft.getInstance().getLanguageManager().getLanguage().getCode();
        Set<String> chineseLangs = Sets.newHashSet("zh_cn", "zh_tw", "zh_hk", "lzh");
        return chineseLangs.contains(language);
    }

    public static void autoSwitchLanguage() {
        if (ModConfigs.misc.autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(ModPlatform.getGameDir().resolve("options.txt"));
                String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageUtils.getFixedLanguage(lang));
            } catch (Exception e) {
                ModContexts.LOGGER.warn("Failed to switch language: ", e);
            }
        }
    }
}
