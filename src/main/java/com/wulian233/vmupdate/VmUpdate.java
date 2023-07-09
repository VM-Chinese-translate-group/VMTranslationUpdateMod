package com.wulian233.vmupdate;

import net.minecraft.Util;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Mod("vmupdate")
public class VmUpdate {
    public VmUpdate() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,configSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> MinecraftForge.EVENT_BUS.register(this));
    }

    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        Player player = event.getPlayer();
        String localVersion = getVersionInConfig().get();
        String onlineVersion = getOnlineVersion(player);
        String Downloadurl = getDownloadurlInConfig().get();

        if (onlineVersion != null && !localVersion.equals(onlineVersion)) {
            player.sendMessage(new TranslatableComponent("vmupdate.message.update", player.getDisplayName().getString(), localVersion, getOnlineVersion(player)), Util.NIL_UUID);

            Component message = new TranslatableComponent("vmupdate.message.update2")
                    .append(new TranslatableComponent(Downloadurl).withStyle(
                            Style.EMPTY
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Downloadurl))
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("vmupdate.message.hover")))
                                    .withColor(net.minecraft.ChatFormatting.AQUA)
                    ))
                    .append(new TranslatableComponent("vmupdate.message.update3"));

            player.sendMessage(message, Util.NIL_UUID);
        }
    }



    public static ForgeConfigSpec configSpec;
    public static ForgeConfigSpec.ConfigValue<String> updateurl,downloadurl,version;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("VM汉化组汉化检测配置");
        updateurl = builder.comment("获取TXT检测更新的url").define("updateurl", "https://vmct-cn.top/sb3/update.txt");
        downloadurl = builder.comment("提示玩家下载地址的url").define("downloadurl", "https://vmct-cn.top/sb3/");
        version = builder.comment("当前汉化版本").define("version", "第一版");
        builder.pop();
        configSpec = builder.build();
    }

    private static ForgeConfigSpec.ConfigValue<String> getUpdateurlInConfig(){return updateurl;}
    private static ForgeConfigSpec.ConfigValue<String> getDownloadurlInConfig(){return downloadurl;}
    private static ForgeConfigSpec.ConfigValue<String> getVersionInConfig(){return version;}
    private String getOnlineVersion(Player player) {
        try {
            String urlStr = getUpdateurlInConfig().get();
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(10000);
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmUpdate";
            connection.setRequestProperty("User-Agent", userAgent);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(new TranslatableComponent("vmupdate.message.error"), Util.NIL_UUID);
            return "";
        }
    }
}