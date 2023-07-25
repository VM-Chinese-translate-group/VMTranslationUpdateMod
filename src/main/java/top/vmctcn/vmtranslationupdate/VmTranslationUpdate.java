package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Mod(modid = "vmtranslationupdate", name = "VM Translation Update", version = "1.4")
public class VmTranslationUpdate {
    public Random random;
    public final List<String> messagesList = new ArrayList<>();
    public int tickCounter;
    public static int minutes;
    public static Configuration config;
    public static String updateurl,downloadurl,tipsurl,version;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
        MinecraftForge.EVENT_BUS.register(this);
        random = new Random();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        String onlineVersion = getOnlineVersion(player);
        String name = player.getName();
        if (name.equals("Zi__Min")) {
            name = "岷叔";
            player.sendMessage(new TextComponentString("欢迎来到籽岷的Minecraft游戏世界"));
        }

        if (onlineVersion != null && !version.equals(onlineVersion)) {
            player.sendMessage(new TextComponentString("VM汉化组：你好"+name+"，当前汉化已过时，请更新你的汉化！当前版本为"+version+"，最新版为"+getOnlineVersion(player)+"。"));

            ITextComponent message = new TextComponentString("请点击右侧").appendSibling(
                    new TextComponentTranslation(downloadurl)
                            .setStyle(new Style()
                                    .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadurl))
                                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("点我到官网下载新版本！")))
                                    .setColor(TextFormatting.AQUA)
                            )
            ).appendSibling(new TextComponentString("下载新版本。"));

            player.sendMessage(message);
        }
    }

    public static void syncConfig() {
        updateurl = config.get("VM汉化组汉化检测配置", "updateurl", "https://vmct-cn.top/rad/update.txt", "获取TXT检测更新的url").getString();
        downloadurl = config.get("VM汉化组汉化检测配置", "downloadurl", "https://vmct-cn.top/rad/", "提示玩家下载地址的url").getString();
        tipsurl = config.get("VM汉化组汉化检测配置", "tipsurl", "https://vmct-cn.top/tips.txt", "获取知识内容的url").getString();
        version = config.get("VM汉化组汉化检测配置", "version", "第一版", "当前汉化版本").getString();
        minutes = config.get("VM汉化组汉化检测配置", "minutes", 25, "发送知识的时间间隔（分钟）").getInt();
        if (config.hasChanged()) {
            config.save();
        }
    }

    public String getOnlineVersion(EntityPlayer player) {
        try {
            URL url = new URL(updateurl);
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(10000);
            connection.setUseCaches(false);
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmTranslationUpdate";
            connection.setRequestProperty("User-Agent", userAgent);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(new TextComponentString("VM汉化组：错误！汉化更新检测出现问题。"));
            return "";
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().world != null) {
            tickCounter++;
            int tickInterval = 20 * 60 * minutes;
            if (tickCounter >= tickInterval) {
                tickCounter = 0;
                String randomMessage = getRandomMessageFromURL(tipsurl);
                if (randomMessage != null) {
                    Minecraft minecraft = Minecraft.getMinecraft();
                    if (minecraft.player != null) {
                        minecraft.player.sendMessage(new TextComponentString(randomMessage));
                    }
                }
            }
        }
    }

    public String getRandomMessageFromURL(String tipsurl) {
        if (messagesList.isEmpty()) {
            loadMessagesFromURL(tipsurl);
        }
        if (!messagesList.isEmpty()) {
            int index = random.nextInt(messagesList.size());
            return messagesList.get(index);
        }
        return null;
    }

    public void loadMessagesFromURL(String tipsurl) {
        try {
            URL url = new URL(tipsurl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                messagesList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}