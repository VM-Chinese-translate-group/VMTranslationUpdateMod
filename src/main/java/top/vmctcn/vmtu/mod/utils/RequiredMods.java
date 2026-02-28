package top.vmctcn.vmtu.mod.utils;

import org.jetbrains.annotations.Nullable;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.config.ModConfigs;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public enum RequiredMods {
    I18N_UPDATE_MOD(
            "i18nupdatemod.I18nUpdateMod",
            "I18nUpdateMod",
            UrlUtils.getCurseForgeModFileUrl("i18nupdatemod"),
            ModConfigs.modInstallCheck.i18nUpdateMod,
            true
    ),
    VAULT_PATCHER(
            "me.fengming.vaultpatcher_asm.VaultPatcher",
            "VaultPatcher",
            UrlUtils.getCurseForgeModFileUrl("vault-patcher"),
            ModConfigs.modInstallCheck.vaultPatcher,
            true
    );

    final String className;
    final String name;
    @Nullable final URL url;
    final boolean loadedCheck;
    final boolean required;

    RequiredMods(@Nullable String className, String name, String url, boolean loadedCheck, boolean required) {
        this.className = className;
        this.name = name;
        this.url = UrlUtils.getUrl(url);
        this.loadedCheck = loadedCheck;
        this.required = required;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public boolean hasUrl() {
        return url != null;
    }

    public URL getUrl() {
        if (!hasUrl()) {
            ModContexts.LOGGER.warn("Invalid URL for mod " + getName() + "!");
        }
        return url;
    }

    public boolean isLoadedCheck() {
        return loadedCheck;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isLoaded() {
        return ModContexts.isModClassLoaded(className);
    }

    public void openUrl() {
        if (hasUrl()) {
            try {
                Desktop.getDesktop().browse(getUrl().toURI());
            } catch (URISyntaxException | IOException exception) {
                ModContexts.LOGGER.warn("Cannot handle URL for mod " + getName() + "!", exception);
            }
        } else {
            ModContexts.LOGGER.info("No URL found for mod " + getName() + " (" + getClassName() + ")!");
        }
    }

    public static ArrayList<RequiredMods> getMissing(boolean required) {
        return ArrayUtils.asArrayList(Arrays.stream(values())
                .filter(dep -> (dep.isRequired() || !required) && !dep.isLoaded() && dep.isLoadedCheck())
        );
    }

    public static ArrayList<RequiredMods> getAllMissing() {
        return ArrayUtils.asArrayList(Arrays.stream(values()).filter(dep -> !dep.isLoaded() && dep.isLoadedCheck()));
    }

    public static boolean isLoaded(boolean required) {
        return !getMissing(required).stream().findAny().isPresent();
    }

    public static boolean isAllLoaded() {
        return !getAllMissing().stream().findAny().isPresent();
    }

    public static String asString(boolean required) {
        return getMissing(required).stream().map(RequiredMods::getName)
                .reduce((a, b) -> a + ", " + b).orElse("");
    }
}
