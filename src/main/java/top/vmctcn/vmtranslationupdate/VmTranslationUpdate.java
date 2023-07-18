package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Mod(modid = "vmtranslationupdate", name = "VM Chinese Group Translation Update", version = "1.2")
public class VmTranslationUpdate {
    private Random random;
    private int tickCounter;
    private static int minutes;
    public static Configuration config;
    public static String updateurl,downloadurl,version;

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
        String localVersion = version;
        String onlineVersion = getOnlineVersion(player);
        String name = player.getDisplayNameString();
        if (name.equals("Zi__Min")) {
            name = "岷叔";
            player.sendMessage(new TextComponentString("欢迎来到籽岷的Minecraft游戏世界！"));
        }

        if (onlineVersion != null && !localVersion.equals(onlineVersion)) {
            player.sendMessage(new TextComponentString("VM汉化组：你好"+name+"，当前汉化已过时，请更新你的汉化！当前版本为"+localVersion+"，最新版为"+getOnlineVersion(player)+"。"));

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
        version = config.get("VM汉化组汉化检测配置", "version", "第一版", "当前汉化版本").getString();
        minutes = config.get("VM汉化组汉化检测配置", "minutes", 25, "发送消息的时间间隔（分钟）").getInt();
        if (config.hasChanged()) {
            config.save();
        }
    }


    private String getOnlineVersion(EntityPlayer player) {
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
                sendRandomMessage();
            }
        }
    }


    private void sendRandomMessage() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.player != null) {
            String message = getRandomMessage();
            minecraft.player.sendMessage(new TextComponentString(message));
        }
    }

    private String getRandomMessage() {
        String[] messages = {
                "你知道吗： 凋零是一种状态效果，凋灵是一种敌对生物。因此绝大多数情况下应称为凋灵。",
                "你知道吗： 拧字有3个读音，向两个方向使劲叫2声拧，拧干。一个方向是3声，拧螺丝。",
                "你知道吗： 荧石是一种发光方块，火字底。萤石是氟化钙，虫字底。因此绝大多数情况下应称为荧石。",
                "你知道吗： 地狱在1.16后更名为下界，因此高版本都是下界。",
                "你知道吗： 曲字有2个读音，带弯的念1声，曲线。乐曲音乐是3声，曲艺。",
                "你知道吗： 髓有且只有一个读音！读精髓。",
                "你知道吗： 看对联最后一个字平仄声。3声4声是上联，贴右边，别贴反啦。",
                "你知道吗： 新版MC的菌丝已经改为菌丝体了。",
                "你知道吗： 新版MC的速度已经改为迅捷了。",
                "你知道吗： 新版MC的末影水晶已经改为末地水晶了。",
                "你知道吗： 新版MC的干草块已经改为干草捆了。",
                "你知道吗： 新版MC的摔落保护已经改为摔落缓冲了。",
                "你知道吗： “因为”的“为”念4声。",
                "你知道吗： 这是VM汉化组汉化更新检测模组发出的一条消息。"
        };

        int index = random.nextInt(messages.length);
        return messages[index];
    }
}

