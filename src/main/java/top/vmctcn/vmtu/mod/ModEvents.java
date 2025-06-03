package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.text.*;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

@Mod.EventBusSubscriber(modid = VMTranslationUpdate.MOD_ID)
public class ModEvents {
    public boolean firstTitleScreenShown = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.player;
        String localVersion = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getVersion();
        String onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigs.testMode) {
            ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpack.getTranslation();

            player.sendMessage(new LiteralText("==================== VMTU testMode ===================="));
            player.sendMessage(new LiteralText("Modpack Name: " + modpack.getName()));
            player.sendMessage(new LiteralText("Modpack Version: " + modpack.getVersion()));
            player.sendMessage(new LiteralText("Modpack Translation URL:§b " + translation.getUrl()));
            player.sendMessage(new LiteralText("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()));
            player.sendMessage(new LiteralText("Modpack Translation Language: " + translation.getLanguage()));
            player.sendMessage(new LiteralText("Modpack Translation Version: " + translation.getVersion()));
            player.sendMessage(new LiteralText("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()));
        }

        if (ModConfigs.checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.error"));
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                String updateUrl = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUrl();
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", localVersion, onlineVersion));

                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(updateUrl)
                                .setStyle(new Style()
                                        .setClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .setHoverEvent(GameEventHelper.hoverShowText(new TranslatableText("vmtranslationupdate.message.hover")))
                                        .setColor(Formatting.AQUA)
                                ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));
                player.sendMessage(message);
            }
        }
    }



    @SubscribeEvent
    public void screenAfterInitEvent(GuiScreenEvent.InitGuiEvent.Pre event) {
        if (firstTitleScreenShown || !(event.getGui() instanceof TitleScreen)) {
            return;
        }

        String language = Minecraft.getInstance().getLanguageManager().getLanguage().getCode();

        if ("zh_cn".equals(language)) {
            boolean needI18n = ModConfigs.i18nUpdateModCheck && !SuggestModScreen.i18nUpdateModPresent;
            boolean needVP = ModConfigs.vaultPatcherCheck && !SuggestModScreen.vaultPatcherPresent;

            // 只要有任何一个模组需要提示，就显示屏幕
            if (needI18n || needVP) {
                Minecraft.getInstance().openScreen(new SuggestModScreen(event.getGui()));
            }
        }

        firstTitleScreenShown = true;
    }
}