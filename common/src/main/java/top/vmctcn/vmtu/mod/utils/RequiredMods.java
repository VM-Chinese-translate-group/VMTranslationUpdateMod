package top.vmctcn.vmtu.mod.utils;

import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import top.vmctcn.vmtu.core.util.ArrayUtil;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public enum RequiredMods {
    I18N_UPDATE_MOD(
            "i18nupdatemod.I18nUpdateMod",
            "I18nUpdateMod",
            UrlUtils.getCurseForgeModFileUrl("i18nupdatemod"),
            ModConfigHelper.getConfig().modInstallCheck.i18nUpdateMod,
            true
    ),
    VAULT_PATCHER(
            "me.fengming.vaultpatcher_asm.VaultPatcher",
            "VaultPatcher",
            UrlUtils.getCurseForgeModFileUrl("vault-patcher"),
            ModConfigHelper.getConfig().modInstallCheck.vaultPatcher,
            true
    ),
    TEXTURE_LOCALE_REDIRECTOR(
            "com.wulian.texturelocaleredirector.TextureLocaleRedirector",
            "TextureLocaleRedirector",
            UrlUtils.getCurseForgeModFileUrl("texture-locale-redirector"),
            ModConfigHelper.getConfig().modInstallCheck.textureLocaleRedirector,
            false
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
                Util.getPlatform().openUri(getUrl().toURI());
            } catch (URISyntaxException uriSyntaxException) {
                ModContexts.LOGGER.warn("Cannot handle URL for mod " + getName() + "!", uriSyntaxException);
            }
        } else {
            ModContexts.LOGGER.info("No URL found for mod " + getName() + " (" + getClassName() + ")!");
        }
    }

    public static ArrayList<RequiredMods> getMissing(boolean required) {
        return ArrayUtil.asArrayList(Arrays.stream(values())
                .filter(dep -> (dep.isRequired() || !required) && !dep.isLoaded() && dep.isLoadedCheck())
        );
    }

    public static ArrayList<RequiredMods> getAllMissing() {
        return ArrayUtil.asArrayList(Arrays.stream(values()).filter(dep -> !dep.isLoaded() && dep.isLoadedCheck()));
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
