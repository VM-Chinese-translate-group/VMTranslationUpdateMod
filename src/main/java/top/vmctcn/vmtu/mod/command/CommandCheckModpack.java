package top.vmctcn.vmtu.mod.command;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.source.CommandSource;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;

public class CommandCheckModpack extends AbstractCommand {
    @Override
    public String getName() {
        return "modpack";
    }

    @Override
    public String getUsage(CommandSource source) {
        return ModContexts.getTranslationKey("command", "check", "modpack", "usage");
    }

    @Override
    public void run(MinecraftServer server, CommandSource source, String[] args) throws CommandException {
        for (PlayerEntity player : source.getCommandSourceWorld().players) {
            ModEvents.checkModpackUpdateCommand(player);
        }
    }
}
