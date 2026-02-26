package top.vmctcn.vmtu.mod.command;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.source.CommandSource;
import net.minecraftforge.server.command.CommandTreeBase;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;

public class CommandCheck extends CommandTreeBase {

    public CommandCheck() {
        addSubcommand(new CommandCheckModpack());
        addSubcommand(new CommandCheckTranslation());
    }

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getUsage(CommandSource source) {
        return ModContexts.getTranslationKey("command", "check", "usage");
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
