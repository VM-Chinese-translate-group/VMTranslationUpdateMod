package top.vmctcn.vmtu.multiversion;


import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.text.Text;

public class Messages {
    public static void displayClientMessage(PlayerEntity player, Text message, boolean overlay) {
        player.sendMessage(message);
    }

    public static void displayClientMessage(PlayerEntity player, Text message) {
        displayClientMessage(player, message, false);
    }
}
