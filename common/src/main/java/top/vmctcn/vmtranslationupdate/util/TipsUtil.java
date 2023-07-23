package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import top.vmctcn.vmtranslationupdate.config.ConfigScreen;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdateMod;

public class TipsUtil {
    public static void sendRandomMessage() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft.player != null) {
            String message = getRandomMessage();
            minecraft.player.sendMessage(new LiteralText(message), false);
        }
    }
    public static String getRandomMessage() {
        String[] messages = {
                "你知道吗：凋零是一种状态效果，凋灵是一种敌对生物。因此绝大多数情况下应称为凋灵。",
                "你知道吗：拧字有3个读音，向两个方向使劲叫2声拧，拧干。一个方向是3声，拧螺丝。",
                "你知道吗：荧石是一种发光方块，火字底。萤石是氟化钙，虫字底。因此绝大多数情况下应称为荧石。",
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

        int index = VMTranslationUpdateMod.random.nextInt(messages.length);
        return messages[index];
    }

    public static Integer getMinutes() {
        ConfigUtil.getConfig();
        return ConfigScreen.minutes;
    }
}
