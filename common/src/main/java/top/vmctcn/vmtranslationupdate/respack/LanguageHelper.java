package top.vmctcn.vmtranslationupdate.respack;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class LanguageHelper {
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
                    VMTranslationUpdate.LOGGER.error("Failed to get locale from field " + f.getName(), e);
                    return null;
                }
            }).filter(Objects::nonNull).filter(l -> !l.getLanguage().isEmpty() && !l.getCountry().isEmpty()).toArray(Locale[]::new); // Get preset locales from java

            String[] languageCodes = Arrays.stream(locales).map(l -> (l.getLanguage() + "_" + l.getCountry()).toLowerCase()).distinct().toArray(String[]::new); // Get preset language codes from locales
            String fixedFrom = language;

            if (Arrays.stream(languageCodes).anyMatch(l -> l.split("_")[1].equals(Locale.getDefault().getCountry().toLowerCase()))) {
                language = Arrays.stream(languageCodes).filter(l -> l.split("_")[1].equals(Locale.getDefault().getCountry().toLowerCase())).findFirst().orElse("en_us");
            }

            /* Language should be fixed */
            VMTranslationUpdate.LOGGER.info("Switching language to " + language + (!fixedFrom.equals(language) ? ", fixed from " + fixedFrom + "." /* Log the unfixed language */ : "..."));
            return language;
        }

        return DEFAULT_LANGUAGE;
    }
}
