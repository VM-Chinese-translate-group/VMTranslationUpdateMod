package top.vmctcn.vmtu.mod.command;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.server.command.source.CommandSource;
import top.vmctcn.vmtu.mod.ModEvents;

public class CommandCheck extends AbstractCommand {

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getUsage(CommandSource source) {
        return "vmtu.command.check.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canUse(MinecraftServer server, CommandSource source) {
        return true;
    }

    @Override
    public void run(MinecraftServer server, CommandSource source, String[] args) {
        for (PlayerEntity player : source.getCommandSourceWorld().players) {
            ModEvents.playerJoinEvent(player);
        }
    }
}
