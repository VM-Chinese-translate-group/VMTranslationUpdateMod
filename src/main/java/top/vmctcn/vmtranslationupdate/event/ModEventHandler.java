package top.vmctcn.vmtranslationupdate.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfig;
import top.vmctcn.vmtranslationupdate.util.PackDownloadUtil;
import top.vmctcn.vmtranslationupdate.util.TipsUtil;
import top.vmctcn.vmtranslationupdate.util.VersionCheckUtil;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ModEventHandler {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        String localVersion = ModConfig.translationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);
        String name = player.getName();

        if (name.equals("Zi__Min")) {
            name = "岷叔";
            player.sendMessage(new TextComponentTranslation("vmtranslationupdate.message.zimin"));
        }

        if (!localVersion.equals(onlineVersion)) {
            player.sendMessage(new TextComponentTranslation("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
            ITextComponent message = new TextComponentTranslation("vmtranslationupdate.message.update2")
                    .appendSibling(new TextComponentTranslation(ModConfig.downloadUrl).setStyle(
                            new Style()
                                    .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ModConfig.downloadUrl))
                                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("vmtranslationupdate.message.hover")))
                                    .setColor(TextFormatting.AQUA)
                    ))
                    .appendSibling(new TextComponentTranslation("vmtranslationupdate.message.update3"));

            player.sendMessage(message);
        }

        if (new File(PackDownloadUtil.resourcePackDir.toFile(), PackDownloadUtil.resourcePackName).exists() &&!VMTranslationUpdate.client.gameSettings.resourcePacks.contains(PackDownloadUtil.resourcePackName) && !VMTranslationUpdate.client.gameSettings.resourcePacks.contains("file/" + PackDownloadUtil.resourcePackName)) {
            ITextComponent message = new TextComponentTranslation("vmtranslationupdate.message.pack", ModConfig.packName).setStyle(new Style().setColor(TextFormatting.GOLD));
            player.sendMessage(message);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().world != null) {
            VMTranslationUpdate.tickCounter++;
            int tickInterval = 20 * 60 * TipsUtil.getMinutes();
            if (VMTranslationUpdate.tickCounter >= tickInterval) {
                VMTranslationUpdate.tickCounter = 0;
                CompletableFuture.supplyAsync(() -> TipsUtil.getRandomMessageFromURLAsync(ModConfig.tipsUrl))
                        .thenAccept(message -> {
                            String randomMessage = TipsUtil.getRandomMessageFromURL(ModConfig.tipsUrl);
                            if (message != null) {
                                if (VMTranslationUpdate.client.player != null) {
                                    VMTranslationUpdate.client.player.sendMessage(new TextComponentString(randomMessage));
                                }
                            }
                        });
            }
        }
    }
}