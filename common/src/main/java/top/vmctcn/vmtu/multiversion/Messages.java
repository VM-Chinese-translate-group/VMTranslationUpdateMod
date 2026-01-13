package top.vmctcn.vmtu.multiversion;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Messages {
    public static void displayClientMessage(Player player, Component message, boolean overlay) {
        player.displayClientMessage(message, overlay);
    }

    public static void displayClientMessage(Player player, Component message) {
        displayClientMessage(player, message, false);
    }
}
