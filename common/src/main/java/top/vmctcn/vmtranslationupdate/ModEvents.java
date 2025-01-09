package top.vmctcn.vmtranslationupdate;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

import top.vmctcn.vmtranslationupdate.util.VersionCheckUtil;

public class ModEvents {
    public static void playerJoinEvent(ServerPlayerEntity player) {
        String name = player.getName().getString();
        String localVersion = ModConfigUtil.getConfig().modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

        if (ModConfigUtil.getConfig().checkModPackTranslationUpdate && !localVersion.equals(onlineVersion)) {
            player.sendMessage(Text.translatable("vmtranslationupdate.message.update", name, localVersion, onlineVersion));
            Text message = Text.translatable("vmtranslationupdate.message.update2")
                    .append(Text.translatable(ModConfigUtil.getConfig().modPackTranslationUrl)
                            .setStyle(Style.EMPTY
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ModConfigUtil.getConfig().modPackTranslationUrl))
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("vmtranslationupdate.message.hover")))
                                    .withColor(Formatting.AQUA)
                            ))
                    .append(Text.translatable("vmtranslationupdate.message.update3"));

            player.sendMessage(message);
        }
    }
}
